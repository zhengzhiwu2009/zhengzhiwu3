<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>

<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equalsIgnoreCase("null")) str = "";
		return str.trim();
	}
%>
<%
	String search = fixnull(java.net.URLDecoder.decode(fixnull(request.getParameter("search")),"UTF-8"));
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
	
	String urlexcel="studentinfo_admin_excel.jsp";
	String urlword ="studentinfo_admin_pdf.jsp";
	
	if(!us.getRoleId().equals("3"))  //表示为一级主管和一级辅导员
	{
		urlexcel="studentinfo_subadmin_excel.jsp";
		urlword ="studentinfo_subadmin_pdf.jsp";
	}
	
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
		var url ="/entity/manager/information/infoprint/<%=urlword%>?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id=<%=enterprise_id %>&batch_id=<%=batch_id%>&bSub=<%=bSub %>&erji_id=<%=erji_id%>";
		window.location=url;
	}
}

function getExcel(){

	var eninfo = document.getElementById("enterprise_id").value;
	var xqinfo = document.getElementById("batch_id").value;
	var erji_id = document.getElementById("erji_id").value;
	if(xqinfo==""){
		alert("请先选择学期");
	}else{
		var url ="/entity/manager/basic/studentExcellence_list_excel.jsp?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id="+eninfo+"&batch_id="+xqinfo+"&bSub=<%=bSub %>&erji_id="+erji_id;
		window.location= url;
	}
}

function getAllExcel(){

	var eninfo = document.getElementById("enterprise_id").value;
	var xqinfo = document.getElementById("batch_id").value;
	var erji_id = document.getElementById("erji_id").value;
	if(xqinfo==""){
		alert("请先选择学期");
	}else{
		var url ="/entity/manager/basic/studentExcellence_alllist_excel.jsp?pageInt1=<%=pageInt1%>&name=<%=java.net.URLEncoder.encode(name,"UTF-8")%>&enterprise_id="+eninfo+"&batch_id="+xqinfo+"&bSub=<%=bSub %>&erji_id="+erji_id;
		window.location= url;
	}
}

function changeSelect(batch_id,ent_id,erji_id)
{
	window.navigate("/entity/manager/basic/StudentExcellence_list.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id);
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
               
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看优秀学员列表</td>
             
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
<form name="searchForm" action="/entity/manager/information/infoprint/printlist_student.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
			<%
				String sql_batch ="select * from pe_bzz_batch t order by t.start_time" ; 
				rs = db.executeQuery(sql_batch);
			%>
				 学期:<select id="batch_id"  name="batch_id" onchange="changeSelect(this.value,enterprise_id.value,erji_id.value)">
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
			<select id="enterprise_id" name="enterprise_id" onchange="changeSelect(batch_id.value,this.value,erji_id.value)">
	 			 <option value="">请选择一级企业</option> 
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
			<select id="erji_id" name="erji_id" onchange="changeSelect(batch_id.value,enterprise_id.value,this.value)">
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
			<br/>
          	<span class="link">
          		&nbsp;&nbsp;&nbsp;&nbsp;<a id ="getexcel" name="getexcel" href = "#" onclick="getAllExcel();" >导出(全部)EXCEL</a>
          		&nbsp;&nbsp;&nbsp;&nbsp;<a id ="getexcel" name="getexcel" href = "#" onclick="getExcel();" >导出(审核通过)EXCEL</a>
          	<!--  <font color="red">（此信息仅供参考）</font>-->
          	</span>
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
		
		<script>
		
			function checkPageValue(item){

				if(checkStu_id()){
					
					document.userform.action = "StudentExcellence_edit.jsp?type="+item;
					document.userform.submit();
				}else{
					alert("请至少选择一名学员信息！");
					return ;
				}

			}	
			
			function checkStu_id(){
				var flag = false;
				var stu_ids = document.userform.stu_id;
				
				if(stu_ids != null){
					if(stu_ids.length == undefined){
						if(stu_ids.checked == true){
							flag = true;
						}
					}else{
						for(var a=0;a<stu_ids.length;a++){
							if(stu_ids[a].checked == true){
								flag = true;
								break;
							}
						}
					}
				}
				
				return flag;
			}
			
		
		</script>
		
		<form name='userform' action='#' method='post' class='nomargin' onsubmit="">
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <th width='5%' style='white-space: nowrap;'> <span class="link"> &nbsp; </span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">系统编号</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">姓名</span> 
              </th>
              <th width='5%' style='white-space: nowrap;'><span class="link">性别</span> 
              </th>
               <th width='8%' style='white-space: nowrap;'><span class="link">是否是优秀学员</span> 
              </th>
              <th width='18%' style='white-space: nowrap;'><span class="link">所在企业</span> 
              </th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">移动电话</span></th>
              <th width='6%' style='white-space: nowrap;'> <span class="link">综合成绩</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">论坛交流次数</span></th>
            </tr>
<%
				String sql_en="";
                
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
			
				if(!enterprise_id.equals(""))
				{   
					sql_en="and e.fk_parent_id = '"+enterprise_id+"'";
					if(erji_id.equals(""))
					{
						
					}
					else
					{
						sql_en =" and e.id = '"+erji_id+"'";
					}
				}
				//System.out.println("sql_en:"+sql_en);
				String sql_t = "";
	if((!enterprise_id.equals(""))||(!batch_id.equals(""))){	
	   if(us.getRoleId().equals("3"))
	   {
		   sql_t =  
	    	   "--总人数\n" +
	    	   "select s.id            as id,\n" + 
	    	   "       s.reg_no        as reg_no,\n" + 
	    	   "       s.true_name     as name,\n" + 
	    	   "       e.name          as enterprise_name,\n" + 
	    	   "       s.gender        as gender,\n" + 
	    	   "       pbe.total_grade as grade,\n" + 
	    	   "       s.mobile_phone  as phone,nvl(forum.articlenum,0) as forumnum ,\n" + 
	    	   "       k2.name as is_goodstu , k3.name as  submit_status \n"+
	    	   "  from pe_bzz_student s, pe_enterprise e, sso_user u, pe_bzz_examscore pbe,whatyforum.whatyforum_userinfo forum ,pe_bzz_goodstu k1 , enum_const k2 , enum_const k3 \n" + 
	    	   " where s.fk_enterprise_id = e.id\n" + 
	    	   "   and s.is_goodstu = k2.id(+) and s.id = k1.fk_studentid(+) and k1.submit_status = k3.id(+) \n"+
	    	   "   and u.id = s.fk_sso_user_id\n" + 
	    	   "   and u.flag_isvalid = '2'\n" + 
	    	   "   and pbe.total_grade = '优秀'\n" + 
	    	   "   and pbe.student_id = s.id\n" + 
	    	   "   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
	    	   "   and s.fk_batch_id = '"+batch_id+"' and forum.username(+) = u.login_id \n" + 
	    	  sql_en+" order by e.id , s.reg_no";
	   }
	   else
	   {
		   sql_t =  
	    	   "--总人数\n" +
	    	   "select s.id            as id,\n" + 
	    	   "       s.reg_no        as reg_no,\n" + 
	    	   "       s.true_name     as name,\n" + 
	    	   "       e.name          as enterprise_name,\n" + 
	    	   "       s.gender        as gender,\n" + 
	    	   "       pbe.total_grade as grade,\n" + 
	    	   "       s.mobile_phone  as phone,nvl(forum.articlenum,0) as forumnum ,\n" + 
	    	   "       k2.name as is_goodstu , k3.name as  submit_status \n"+
	    	   "  from pe_bzz_student s, pe_enterprise e, sso_user u, pe_bzz_examscore pbe,whatyforum.whatyforum_userinfo forum ,pe_bzz_goodstu k1 , enum_const k2 , enum_const k3 \n" + 
	    	   " where s.fk_enterprise_id = e.id\n" + 
	    	   "   and s.is_goodstu = k2.id(+) and s.id = k1.fk_studentid(+) and k1.submit_status = k3.id(+) \n"+
	    	   "   and u.id = s.fk_sso_user_id\n" + 
	    	   "   and u.flag_isvalid = '2'\n" + 
	    	   "   and pbe.total_grade = '优秀'\n" + 
	    	   "   and pbe.student_id = s.id\n" + 
	    	   "   and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
	    	   "   and s.fk_batch_id = '"+batch_id+"' and forum.username(+) = u.login_id  and s.fk_enterprise_id in \n" + 
	    	 sql_ent+ sql_en+" order by e.id , s.reg_no";
	   }
                //System.out.println(sql_t);
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
				//rs = db.executeQuery(sql_t);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String id = fixnull(rs.getString("id"));
					String reg_no = fixnull(rs.getString("reg_no"));
					String stuname = fixnull(rs.getString("name"));
					String gender = fixnull(rs.getString("gender"));
					String enterprise_name = fixnull(rs.getString("enterprise_name"));
					String phone = fixnull(rs.getString("phone"));
					String grade = fixnull(rs.getString("grade"));
					String forumnum = fixnull(rs.getString("forumnum"));
					
					String is_goodstu = fixnull(rs.getString("is_goodstu"));
					String submit_status = fixnull(rs.getString("submit_status"));
					
					if(!"".equals(submit_status)){
						is_goodstu = is_goodstu+"("+submit_status+")";
					}
					
%>
            <tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>' >     
              <td style='white-space: nowrap;'><input type="checkbox" name="stu_id" value="<%=id %>"></td>        
              <td style='white-space: nowrap;text-align:center;'><%=reg_no%></td>
              <td style='white-space: nowrap;'><%=stuname%></td>
              <td style='white-space: nowrap;'><%=gender%></td>
              <td style='white-space: nowrap;'><%=is_goodstu%></td>
              <td style='white-space: nowrap;'><%=enterprise_name%></td>
              <td style='white-space: nowrap;'><%=phone%></td>   
              <td style='white-space: nowrap;'><%=grade%></td>    
              <td style='white-space: nowrap;'><%=forumnum %></td>    
            </tr>
            <%
            	}
            	db.close(rs);
            %>
          </table>
          <br/>
          <span class="link"><a id ="getexcel" name="getexcel" href = "#" onclick="checkPageValue('yeas')" >[设为优秀]</a></span>&nbsp;&nbsp;
		  <span class="link"><a id ="getexcel" name="getexcel" href = "#" onclick="checkPageValue('no')" >[取消优秀]</a></span>
		  
		  <input type="hidden" name="batch_id" value="<%=batch_id %>">
		  <input type="hidden" name="enterprise_id" value="<%=enterprise_id %>">
		  <input type="hidden" name="erji_id" value="<%=erji_id %>">
		  <input type="hidden" name="pageInt" value="<%=spageInt %>">
			
</form>
  <!-- end:内容区－列表区域 -->
</div>

<!-- 内容区域结束 -->
	</td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
     <%@ include file="./dividepage.jsp" %>
     </td>
  </tr>
</table>
	
<%
	}
%>
</body>
</html>