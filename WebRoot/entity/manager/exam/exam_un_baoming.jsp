<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
%>
<%
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
	String studentName = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("studentName")),"UTF-8"));
	String enterprisename = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("enterprisename")),"UTF-8"));
	String search1 = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search1")),"UTF-8"));
	String c_search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("c_search")),"UTF-8"));
		
	String pageInt1 = fixnull(request.getParameter("pageInt1"));
	String pageInt2 = fixnull(request.getParameter("pageInt"));
	String c_pageInt = fixnull(request.getParameter("c_pageInt"));
	
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	String name = fixnull(request.getParameter("name"));
	String bSub=fixnull(request.getParameter("bSub"));
	String erji_id=fixnull(request.getParameter("erji_id"));
	
	UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us.getPriEnterprises();
	String sql_ent="";
	String sql_entbk="";
	for(int i=0;i<entList.size();i++)
	{
		PeEnterprise e=(PeEnterprise)entList.get(i);
		sql_ent+="'"+e.getId()+"',";
	}
	sql_entbk=sql_ent;
	if(!sql_ent.equals(""))
	{
		sql_ent="("+sql_ent.substring(0,sql_ent.length()-1)+")";
	}
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	String priTag = "2";  //0:总站管理员；1：一级管理员;2：二级管理员
	//判断是一级企业管理员，还是二级企业管理员
	
	if (us.getRoleId().equals("3")) { //表示总站管理员
	
		priTag = "0";
	} else if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006")) {
		priTag = "1";
	} else {
		priTag = "2";
	}
	
	//String urlexcel="exam_student_unbaoming_excel.jsp";
	//String urlword ="studentinfo_admin_pdf.jsp";
	//
	//if(!us.getRoleId().equals("3"))  //表示为一级主管和一级辅导员
	//{
	//	urlexcel="exam_student_unbaoming_excel.jsp";
		//urlword ="studentinfo_subadmin_pdf.jsp";
	//}
	
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


function getExcel(){

	var eninfo = document.getElementById("enterprise_id").value;
	var xqinfo = document.getElementById("batch_id").value;
	var erji_id = document.getElementById("erji_id").value;
	var search = document.getElementById("search").value;
	var studentName = document.getElementById("studentName").value;
	if(xqinfo==""){
		alert("请先选择学期");
	}else{
		var url ="/entity/manager/exam/exam_student_unbaoming_excel.jsp?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id="+eninfo+"&batch_id="+xqinfo+"&bSub=<%=bSub %>&erji_id="+erji_id+"&search="+search+"&studentName="+studentName;
		window.location= url;
	}
}

function changeSelect(batch_id,ent_id,erji_id,search,studentName)
{
	window.navigate("/entity/manager/exam/exam_un_baoming.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id+"&studentName="+studentName+"&search="+search);
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
                 <%
   if(!enterprise_id.equals("")){
  	 String sql_total = "select b.name as enterprise_name  from pe_enterprise b where b.id ='"+enterprise_id+"'";
  	 //System.out.println(sql);
  	 rs = db.executeQuery(sql_total);
		while(rs!=null&&rs.next())
		{ 
		   String enterprise_name = fixnull(rs.getString("enterprise_name"));
		   
  %>
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看<%=enterprise_name %>考试未报名学员列表</td>
             
              </tr>
                <%}
         db.close(rs);
         }
         else {
   %>    
            <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看考试未报名学员列表</td>
             
              </tr>
         
        <%} %>
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
<form name="searchForm" action="/entity/manager/exam/exam_un_baoming.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<%
				String sql_batch ="select * from pe_bzz_batch t order by t.start_time" ; 
				rs = db.executeQuery(sql_batch);
			%>
				 学期:<select id="batch_id"  name="batch_id" onchange="changeSelect(this.value,enterprise_id.value,erji_id.value,search.value,studentName.value)">
				 			<option value="">所有学期</option>
				 			<%
				 				while(rs!=null&&rs.next()){
				 					String selected="";
				 					String t_id=fixnull(rs.getString("id"));
				 					String t_name=fixnull(rs.getString("name"));
				 					if(batch_id.equals(t_id))
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
<%--				<div>--%>
<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
<%--                </div>--%>
			</td>
		  </tr>
		   <%
			 String sql_econ="";
			if(priTag.equals("0")) {  //总站管理员查询全部一企业
				sql_econ = "";
			} else if(priTag.equals("1") || priTag.equals("2")) { //一、二级企业管理员查询所在一级企业
				//sql_econ = " and t.id in " + sql_ent;
				sql_econ = " and t.id in ( "
					+ "select distinct e.fk_parent_id from pe_enterprise e where e.id in "
					+sql_ent+")";
			} 
			String sql_enter = "select * from pe_enterprise t  where t.fk_parent_id is null  "
							+ sql_econ + " order by t.code";
			//System.out.println(sql_enter);
			rs = db.executeQuery(sql_enter);
			%>
		   <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			选择一级企业:
			<select id="enterprise_id" name="enterprise_id" onchange="changeSelect(batch_id.value,this.value,erji_id.value,search.value,studentName.value)">
	 			<option value="">所有</option>
	 			<%
	 				while(rs!=null&&rs.next()){
	 					String selected="";
	 					String t_id=fixnull(rs.getString("id"));
	 					String t_name=fixnull(rs.getString("name"));
	 					String t_code=fixnull(rs.getString("code"));
	 					if (enterprise_id.equals(t_id)) {  //上次选择的企业
							selected = "selected";
						} else if(priTag.equals("1") || priTag.equals("2")) {  //如果是一、二级管理员，只显示所在一级企业
							selected = "selected";
							enterprise_id = t_id;
						}
	 			%>
	 		<option value="<%=rs.getString("id") %>" <%=selected %>>(<%=t_code %>)<%=rs.getString("name")%></option>							 					
	 			<%
	 				}
	 			db.close(rs);
	 			%>
					 </select>
			</div>
			</td>
	     </tr>
	     <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			选择二级企业:
			<select id="erji_id" name="erji_id" onchange="changeSelect(batch_id.value,enterprise_id.value,this.value,search.value,studentName.value)">
	 			<option value="">所有</option>
	 			<%
	 				String sql_erji="";
	 				String t_sql="";
	 				if(priTag.equals("0")) {  //总站管理员可以管理一级企业下所有二级企业
						t_sql = "select id, code, name\n" + "  from pe_enterprise e\n"
						+ " where e.fk_parent_id = '" + enterprise_id + "'"
						+ " order by code";
					} else {  //非总站管理员只可能管理，自己管理企业范围内的二级企业
						t_sql = "select id, code, name\n" + "  from pe_enterprise e\n"
						+ " where e.fk_parent_id = '" + enterprise_id + "' and e.id in " + sql_ent
						+ " order by code";
					}
				
					if ( !erji_id.equals("")) {
						sql_erji = " and e.id='" + erji_id + "'";
						
					} 
					//	System.out.println(t_sql);
					rs = db.executeQuery(t_sql);
	 				while(rs!=null&&rs.next()){
	 					String selected="";
	 					String t_id=fixnull(rs.getString("id"));
	 					String t_name=fixnull(rs.getString("name"));
	 					String t_code=fixnull(rs.getString("code"));
	 					if (erji_id.equals(t_id)) { //上次选择的企业
							selected = "selected";
						} else if(!priTag.equals("0") && sql_ent.indexOf(",") == -1) { //如果非总站管理员用户,且企业管理范围只有一个
							selected = "selected";
						}
	 			%>
	 		<option value="<%=rs.getString("id") %>" <%=selected %>>(<%=t_code %>)<%=rs.getString("name")%></option>							 					
	 			<%
	 				}
	 			db.close(rs);
	 			%>
					 </select>
			</div>
			</td>
	     </tr>
	      <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			系统编号:
			<input type = "text" name = "search" value="<%=search %>"></input>&nbsp;&nbsp;&nbsp;&nbsp;
			姓名
			<input type = "text" name = "studentName" value="<%=studentName %>"></input>&nbsp;&nbsp;&nbsp;&nbsp;
			<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
			</div>
			</td>
	     </tr>
	     <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<br/>
          		<!-- <span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span> -->
          	 <%
          	 if(!us.getLoginId().equals("gzw"))
          	 {
          	  %> <span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a id ="getexcel" name="getexcel" href = "#" onclick="getExcel();" >导出学员信息</a></span>
				&nbsp;&nbsp;&nbsp;
				<%
				}
				 %>
			</div>
			</td>
	     </tr>
		<!--   <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			是否包含二级企业学员:
			<select name="bSub" onchange="changeSelect(batch_id.value,enterprise_id.value,this.value)">
			<option value="1" <%if(bSub.equals("1")) out.print("selected");%>>是</option>
			<option value="0" <%if(bSub.equals("0")) out.print("selected");%>>否</option>
			</select>&nbsp;&nbsp;<font color=red>(所选企业为一级企业时，此项才起作用)</font>
			</div>
			</td>
	     </tr>  -->
	</table>
	</form>	
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
<%--	<form name='userform' action='pici_delexe.jsp' method='post' class='nomargin' onsubmit="">	--%>
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <!--<th width='0' class='select'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
              </th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="详细信息">F</a></span></div></th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link"><a href='#' title="状态设置">A</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="编辑">E</a></span></div></th>
              <th width='20' style='white-space: nowrap;'> <div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="删除">D</a></span></div></th> -->
              <th width='15%' style='white-space: nowrap;'> <span class="link">系统编号</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">姓名</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在学期</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">所在企业</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">移动电话</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">电子邮件</span></th>
              
              <!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
            </tr>
<%
				String sql_con="";
				String sql_en="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
				{
					sql_con+=" and e.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				if(!enterprise_id.equals(""))
				{
					if(erji_id.equals(""))
					{
						sql_en = " and (e.id = '" + enterprise_id + "' or e.fk_parent_id ='"+enterprise_id+"')";
					}
					else
						sql_en =" and e.id = '"+erji_id+"'";
				}
				if(!batch_id.equals(""))
				{
					sql_en +="and b.id = '"+batch_id+"'";
				}
				//System.out.println("sql_en:"+sql_en);
				String sql_t = "";
				String sql_reg="";
				String sql_name="";
	if(search!=null &&!search.equals(""))
	{
		sql_reg=" and s.reg_no like '%"+search+"%'";
	}
	if(studentName!=null && !studentName.equals(""))
	{
		sql_name=" and s.true_name like  '%"+studentName+"%'";
	}
	if(!batch_id.equals("")){			
     /*  sql_t =  "select e.name         as enterprise_name,\n" +
    	   "       s.id           as student_id,\n" + 
    	   "       s.true_name    as student_name,\n" + 
    	   "       s.reg_no       as student_reg_no,\n" + 
    	   "       s.mobile_phone as student_phone,\n" + 
    	   "       s.email        as student_email,\n" + 
    	   "       b.name         as batch_name\n" + 
    	   "  from pe_bzz_batch            b,\n" + 
    	   "       pe_bzz_student          s,\n" + 
    	   "       pe_enterprise           e,\n" + 
    	   " 	   sso_user su "+
    	   " where b.id = s.fk_batch_id\n" + 
    	   "   and s.fk_enterprise_id = e.id\n" + 
    	   " 	and s.fk_sso_user_id=su.id and su.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006'"+
                "   "+sql_en+" "+sql_con+" "+sql_con1+" and s.reg_no like '%"+search+"%' order by reg_no";
        */ 
         
         sql_t=
			"select e.name         as enterprise_name,\n" +
			"       s.id           as student_id,\n" + 
			"       s.true_name    as student_name,\n" + 
			"       s.reg_no       as student_reg_no,\n" + 
			"       s.mobile_phone as student_phone,\n" + 
			"       s.email        as student_email,\n" + 
			"       b.name         as batch_name\n" + 
			"  from pe_bzz_batch b, pe_bzz_student s, pe_enterprise e, sso_user su\n" + 
			" where b.id = s.fk_batch_id\n" + 
			"   and s.fk_enterprise_id = e.id\n" + 
			"   and s.fk_sso_user_id = su.id\n" + 
			"   and su.flag_isvalid = '2'\n" + 
			"   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006' \n" + 
			"   "+sql_en+" "+sql_con+" "+sql_con1+sql_reg+sql_name+
			"minus\n" + 
			"select e.name         as enterprise_name,\n" + 
			"       s.id           as student_id,\n" + 
			"       s.true_name    as student_name,\n" + 
			"       s.reg_no       as student_reg_no,\n" + 
			"       s.mobile_phone as student_phone,\n" + 
			"       s.email        as student_email,\n" + 
			"       b.name         as batch_name\n" + 
			"  from pe_bzz_batch     b,\n" + 
			"       pe_bzz_student   s,\n" + 
			"       pe_enterprise    e,\n" + 
			"       sso_user         su,\n" + 
			"       pe_bzz_examscore es,\n" + 
			"       pe_bzz_examlate  el\n" + 
			" where b.id = s.fk_batch_id\n" + 
			"   and s.fk_enterprise_id = e.id\n" + 
			"   and s.fk_sso_user_id = su.id\n" + 
			"   and su.flag_isvalid = '2'\n" + 
			"   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
			"   and (es.student_id = s.id or el.student_id = s.id) \n" +
			"   "+sql_en+" "+sql_con+" "+sql_con1+sql_reg+sql_name;
			
		}
               
				//----------分页结束---------------
				
				if(batch_id.equals(""))
				{
				%>
			<tr>
           <td style='white-space: nowrap;'><font color="red">请先选择一个学期，进行查找。</font></td>
           </tr>
				<%
				}
				else{
				 int totalItems = 0;
				 totalItems = db.countselect(sql_t);
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
				String link="&batch_id="+batch_id+"&search="+search+"&enterprise_id="+enterprise_id+"&erji_id="+erji_id+"&pageInt1="+pageInt1+"&c_pageInt="+c_pageInt+"&search1="+search1+"&c_search="+c_search+"&bSub="+bSub;
				
				rs = db.execute_oracle_page(sql_t,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String enterprise_name = fixnull(rs.getString("enterprise_name"));
					String student_id = fixnull(rs.getString("student_id"));
					String student_name = fixnull(rs.getString("student_name"));
					String student_phone = fixnull(rs.getString("student_phone"));
					String student_reg_no = fixnull(rs.getString("student_reg_no"));
					String student_email = fixnull(rs.getString("student_email"));
					String batch_name = fixnull(rs.getString("batch_name"));
%>
            <tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>' >             
              <%-- <td class='select' align='center'> <input type='checkbox' class='checkbox' name='ids' value='<%%>' id='<%%>'> 
              </td>
              <td style='text-align: center; '><span class="link" title='详细信息' onClick="window.open('pici_info.jsp?id=<%%>')"><img src='/entity/manager/statistics/images/buttons/csv.png' alt='详细信息' width="20" height="20" title='详细信息'></span> 
              </td>
              <td style='text-align: center; '><% if(){%> <span class="link" title='锁定' ><img src='/entity/manager/statistics/images/buttons/active.png' alt='锁定' width="20" height="20" title='锁定' onClick="javascript:jiesuo('<%%>','<%%>');"></span> <% }else{ %> <span><img src='/entity/manager/statistics/images/buttons/multi_activate.png' alt='解锁' width="20" height="20" title='解锁'></span><%} %>
              </td>
              <td style='text-align: center; '><span class="link" title='编辑'><a href="pici_edit.jsp?pageInt=<%%>&id=<%%>"><img src='/entity/manager/statistics/images/buttons/edit.png' alt='编辑' width="20" height="20" title='编辑' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='删除'><a href="javascript:cfmdel('pici_delexe.jsp?pageInt=<%%>&id=<%%>')"><img src='/entity/manager/statistics/images/buttons/delete.png' alt='删除' width="20" height="20" title='删除' border=0></a></span> 
              </td> --%>

              <td style='white-space: nowrap;text-align:center;'><%=student_reg_no%></td>
              <td style='white-space: nowrap;'><%=student_name%></td>
              <td style='white-space: nowrap;'><%=batch_name%></td>
              <td style='white-space: nowrap;'><%=enterprise_name%></td>
              <td style='white-space: nowrap;'><%=student_phone%></td>   
              <td style='white-space: nowrap;'><%=student_email%></td>    
              <%--<td style='white-space: nowrap;'><%%></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="pici_edu_major.jsp?pici=<%%>&pici_id=<%%>&Search=<%%>&pageInt=<%%>">设置</td> --%>
            </tr>
            <%
            	}
            	db.close(rs);
            %>
          </table>
<%--          </form>--%>
  <!-- end:内容区－列表区域 -->
</div>

<!-- 内容区域结束 -->
	</td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"><%@ include file="./dividepage.jsp" %>
     </td>
  </tr>
</table>
<%
	}
%>
</body>
</html>