<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<%@ include file="./priv.jsp"%>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant" />
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
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
	String exambatch_id = fixnull(request.getParameter("exambatch_id"));
	String question_type = fixnull(request.getParameter("question_type"));
	String is_solve=fixnull(request.getParameter("is_solve"));
	String title_search=fixnull(request.getParameter("title_search"));
	
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	
	dbpool db = new dbpool();
	MyResultSet rs = null;
	MyResultSet rs1 = null;
	MyResultSet rs2 = null;
	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tableType_1</title>
<link href="/entity/manager/statistics/css/admincss.css" rel="stylesheet" type="text/css">
<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + 'form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'ids')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}

</script>
<script  language="javascript">
//	function jiesuo(id,page)
//	{
//		var batch_id = id;
//		var pageInt = page;
//		var link = "pici_jiesuo.jsp?batch_id="+batch_id+"&pageInt="+pageInt;
//		if(confirm("您确定要解锁当前批次吗？")){
//		    window.location.href=link;
//			}
//	}
//	function cfmdel(link)
//	{
//		if(confirm("您确定要删除本批次吗？"))
//			window.navigate(link);
//	}

function doselect(objID)
{
	var tempObj;
		if(document.getElementById(objID))
		{
			tempObj = document.getElementById(objID);
			if(tempObj.checked)
			{
				tempObj.checked=false;
			}
			else
			{
				tempObj.checked=true;
			}
		}
}

function getpdf(){
	
	var eninfo = document.getElementById("enterprise_id").value;
	var xqinfo = document.getElementById("batch_id").value;
	if(eninfo==""){
		alert("请先选择企业");
	}else{
		window.location=url;
	}
}

function getExcel(){

	var eninfo = document.getElementById("enterprise_id").value;
	var xqinfo = document.getElementById("batch_id").value;
	var erji_id = document.getElementById("erji_id").value;
	if(eninfo==""){
		alert("请先选择企业");
	}else{
		window.location= url;
	}
}

function changeSelect(exambatch_id,question_type,is_solve,title_search)
{
	window.navigate("/entity/manager/exam/question_list.jsp?exambatch_id="+exambatch_id+"&question_type="+question_type+"&is_solve="+is_solve+"&title_search="+title_search);
}
</script>
</head>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td height="28"><table width="100%" height="28" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="12"><img src="/entity/manager/statistics/images/page_titleLeft.gif" width="12" height="28"></td>
          <td align="right" background="/entity/manager/statistics/images/page_titleM.gif"><table height="28" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td width="112"><img src="/entity/manager/statistics/images/page_titleMidle.gif" width="112" height="28"></td>
            <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看辅导答疑问题列表</td>
             
              </tr>
         
              </tr>
            </table></td>
          <td width="8"><img src="/entity/manager/statistics/images/page_titleRight.gif" width="8" height="28"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder">
<!-- start:内容区域 -->

<div class="border">
<form name="searchForm" action="/entity/manager/exam/question_list.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<%
				String sql_batch ="select * from pe_bzz_exambatch t "; 
				rs = db.executeQuery(sql_batch);
			%>
				 考试批次:&nbsp;<select id="exambatch_id"  name="exambatch_id" onchange="changeSelect(this.value,question_type.value,is_solve.value,title_search.value)">
				 			<option value="">请选择考试批次</option>
				 			<%
				 				while(rs!=null&&rs.next()){
				 					String selected="";
				 					String t_id=fixnull(rs.getString("id"));
				 					String t_name=fixnull(rs.getString("name"));
				 					if(exambatch_id.equals(t_id))
				 						selected="selected";
				 					%>
				 				<option value="<%=rs.getString("id") %>" <%=selected %>><%=rs.getString("name")%></option>							 					
				 			<%
				 				}
				 			db.close(rs);
				 			%>
					 </select>
          	</div>
			</td>
			<td class='misc' style='white-space: nowrap;'>
			</td>
		  </tr>
		   <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<%
			    String sql_type ="select * from exam_question_type t "; 
				rs2 = db.executeQuery(sql_type);
			%>
			问题类型:
			<select id="question_type" name="question_type" onchange="changeSelect(exambatch_id.value,this.value,is_solve.value,title_search.value)">
	 			<option value="">请选择问题类型</option>
	 			<%
	 				while(rs2!=null&&rs2.next()){
	 					String selected="";
	 					String t_id=fixnull(rs2.getString("id"));
	 					String t_name=fixnull(rs2.getString("name"));
	 					if (question_type.equals(t_name)) {  
							selected = "selected";
						} 
	 			%>
	 		<option value="<%=rs2.getString("name") %>" <%=selected %>>(<%=rs2.getString("id") %>)<%=rs2.getString("name")%></option>							 					
	 			<%
	 				}
	 			db.close(rs2);
	 			%>
					 </select>
			</div>
			</td>
	     </tr>
	     <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			是否解决:
			<select id="is_solve" name="is_solve" onchange="changeSelect(exambatch_id.value,question_type.value,this.value,title_search.value)">
	 			<option value="">所有</option>
	 			<option value="1" <%if(is_solve.equals("1")) out.print("selected");%>>是</option>
			    <option value="0" <%if(is_solve.equals("0")) out.print("selected");%>>否</option>
			</select>
			</div>
			</td>
	     </tr>
	      <tr> 
			<td style='white-space: nowrap;'>
				<div style="padding-left:6px"> 
				问题标题:
				<input type="text" id = "title_search" name = "title_search" value="<%=title_search %>">
				<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
				</div>
			</td>
	     </tr>
	     <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<br/>
          		<!-- <span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span> -->
          	
          	  <span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a  href = "/entity/manager/exam/exam_answer_tw.jsp" >我要提问（当前批次）</a></span>
				&nbsp;&nbsp;&nbsp;
			<!--  <span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a  href = "/entity/manager/exam/exam_answer_tw.jsp" >置顶</a></span>
				&nbsp;&nbsp;&nbsp;
				 <span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a  href = "/entity/manager/exam/exam_answer_tw.jsp" >取消置顶</a></span>
				&nbsp;&nbsp;&nbsp; -->
			</div>
			</td>
	     </tr>
	</table>
	</form>	
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
<%--	<form name='userform' action='pici_delexe.jsp' method='post' class='nomargin' onsubmit="">	--%>
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <th width='25%' style='white-space: nowrap;'> <span class="link">标题</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">问题类型</span></th>
              <th width='15%' style='white-space: nowrap;'> <span class="link">提问者</span> 
              </th>
             <%--  <th width='10%' style='white-space: nowrap;'><span class="link">提问者类型</span> 
              </th>--%>
              <th width='10%' style='white-space: nowrap;'><span class="link">提问时间</span> 
              </th>
              <th width='20%' style='white-space: nowrap;'> <span class="link">考试批次</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">是否解决</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">首页显示</span></th>
              <th width='15%' style='white-space: nowrap;'> <span class="link">操作</span></th>
            </tr>
<%
				   String sql_con="";
				   if(!exambatch_id.equals(""))
				   {
				   		sql_con = sql_con+"and eqi.exambatch_id='"+exambatch_id+"' ";
				   }
				   if(!question_type.equals(""))
				   {
				   		sql_con = sql_con+"and eqt.name='"+question_type+"' ";
				   }
				   if(!is_solve.equals(""))
				   {
				   		if(is_solve.equals("1"))
				   		{
				   			sql_con = sql_con+"and eai.publish_type is not null ";
				   		}
				   		else if(is_solve.equals("0"))
				   		{	
				   			sql_con = sql_con+"and eai.publish_type is null ";
				   		}
				   		
				   }
				   if(!title_search.equals(""))
				   {
				   		sql_con = sql_con+" and eqi.title like '%"+title_search+"%'";
				   }
	               String sql=
					"select question_id,\n" +
					"       question_title,\n" + 
					"       publish_date,\n" + 
					"       exambatch_id,\n" + 
					"       submituser_name,\n" + 
					"       submituser_type,\n" + 
					"       click_num,\n" + 
					"       is_index_show,\n" + 
					"       question_type,\n" + 
					"       publish_type,exambatch_name \n" + 
					"  from (select distinct eqi.id as question_id,\n" + 
					"                        eqi.title as question_title,\n" + 
					"                        to_char(eqi.publish_date, 'yyyy-mm-dd hh24:mi:ss') as publish_date,\n" + 
					"                        eqi.exambatch_id as exambatch_id,\n" + 
					"                        eqi.submituser_name as submituser_name,\n" + 
					"                        eqi.submituser_type as submituser_type,\n" + 
					"                        eqi.click_num as click_num,\n" + 
					"                        nvl(eqi.is_index_show,'0') as is_index_show,\n" + 
					"                        eqt.name as question_type,\n" + 
					"                        eai.publish_type as publish_type,pbe.name as exambatch_name \n" + 
					"          from exam_question_info eqi,\n" + 
					"               exam_question_type eqt,\n" + 
					"               exam_answer_info   eai,pe_bzz_exambatch pbe \n" + 
					"         where eqi.question_type = eqt.id\n" + 
					"           and eai.question_id(+) = eqi.id and pbe.id=eqi.exambatch_id \n" + 
					"           and eai.publish_type(+) = 'manager'\n" + sql_con+
					"         order by eqi.id desc)";
	               System.out.println(sql);
					int totalItems = db.countselect(sql);
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
					String link="&exambatch_id=" + exambatch_id +"&question_type="+question_type+"&is_solve="+is_solve+"&title_search="+title_search;
					//----------分页结束---------------
					rs1 = db.execute_oracle_page(sql,pageInt,pagesize);
					int a = 0;
					while(rs1!=null&&rs1.next())
					{
						a++;
						String question_id = fixnull(rs1.getString("question_id"));
						String question_title = fixnull(rs1.getString("question_title"));
						String publish_date = fixnull(rs1.getString("publish_date"));
						String submituser_name = fixnull(rs1.getString("submituser_name"));
						//String submituser_type = fixnull(rs1.getString("submituser_type"));
						String questiontype = fixnull(rs1.getString("question_type"));
						String publish_type = fixnull(rs1.getString("publish_type"));
						String exambatch_name = fixnull(rs1.getString("exambatch_name"));
						String solve_flag="已解决";
						if(publish_type.equals(""))
						{
							solve_flag="未解决";
						}
						String index_show = "<span style='color:red'>否</span>";
						if("1".equals(fixnull(rs1.getString("is_index_show")))){
							index_show = "<span style='color:green'>是</span>";
						}
%>
            <tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>' >             
              <td style='white-space: nowrap;text-align:center;'><%=subTitle(question_title,28) %></td>
              <td style='white-space: nowrap;text-align:center;'><%=questiontype %></td>
              <td style='white-space: nowrap;text-align:center;'><%=submituser_name %></td>
              <%--<td style='white-space: nowrap;text-align:center;'><%=submituser_type %></td> --%>
              <td style='white-space: nowrap;text-align:center;'><%=publish_date %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=exambatch_name %></td> 
              <td style='white-space: nowrap;text-align:center;'><%=solve_flag %></td>    
              <td style='white-space: nowrap;text-align:center;'><%=index_show %></td>    
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/exam/exam_answer_info.jsp?question_id=<%=question_id %>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>">查看/回答</a>
              &nbsp;&nbsp;<a href="javascript:if(confirm('删除问题会一并删除答案，您确定吗？')) window.location.href=window.location.href='/entity/manager/exam/exam_answer_delete.jsp?question_id=<%=question_id %>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>'">删除</a>
              <a href="/entity/manager/exam/exam_index_show.jsp?question_id=<%=question_id %>&exambatch_id=<%= exambatch_id %>&question_type=<%= question_type %>&is_solve=<%= is_solve %>&title_search=<%= title_search %>">取消/设置首页显示</a>
              </td>       
            </tr>
           <%}
            db.close(rs1); 
          %>
          </table>
<%--          </form>--%>
  <!-- end:内容区－列表区域 -->
</div>

<!-- 内容区域结束 -->
	</td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
       <%@ include file="../pub/dividepage.jsp" %>
     </td>
  </tr>
</table>
</body>
</html>