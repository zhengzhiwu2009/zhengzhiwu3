<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page"/>
<%@page import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.sso.web.action.SsoConstant"/>
<%@ include file="./pub/priv.jsp"%>
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
	dbpool db = new dbpool();
	MyResultSet rs = null;
	
	
	UserSession us1 = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	List entList=us1.getPriEnterprises();
	String enterprise_id = "";
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
	if(us1.getRoleId().equals("402880f322736b760122737a60c40008") || us1.getRoleId().equals("402880f322736b760122737a968a0010"))  //表示为二级主管和二级辅导员
	{
		String t_sql="select fk_parent_id from pe_enterprise where id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			enterprise_id=fixnull(rs.getString("fk_parent_id"));
		}
		db.close(rs);
	}
	if(us1.getRoleId().equals("2") || us1.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}
	String sql_con2="";
	String sql_con1="";
	//if(!search.equals(""))
		//sql_con=" and b.id='"+search+"'";
	if(!sql_ent.equals(""))
	{
		sql_con2+=" and pe.id in "+sql_ent;
		sql_con1=" and s.fk_enterprise_id in "+sql_ent;
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
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看考试统计信息</td>
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
	<form name="searchForm" action="/entity/manager/statistics/stat_exam.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px">选择考试批次： 
				<select name="search">
		                <option value="">所有批次</option>
		                <% 
		                                               
		        String sql_t="select id,name from pe_bzz_exambatch order by id";
				rs=db.executeQuery(sql_t);
				while(rs!=null && rs.next())
				{
				    String id=fixnull(rs.getString("id"));
					String batch_name=fixnull(rs.getString("name"));
					String selected="";
					if(id.equals(search))
							selected="selected";
				%>
			<option value=<%=id %> <%=selected %>><%=batch_name %></option>
			<%
							}
					db.close(rs);
			%>
				</select>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
          	</div>
			</td>
			<td class='misc' style='white-space: nowrap;'>
<%--				<div>--%>
<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
<%--                </div>--%>
			</td>
		  </tr>
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
              <th width='10%' style='white-space: nowrap;'> <span class="link">考试批次名称</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">学习总人数</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">报考人数</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">考试未审核人数</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">考试已审核人数</span> 
              </th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">缓考人数</span></th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">缓考未审核人数</span></th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">缓考已初审人数</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">缓考已终审人数</span> 
              <th width='10%' style='white-space: nowrap;'> <span class="link">缓考已驳回人数</span> 
              <th width='10%' style='white-space: nowrap;'> <span class="link">考试未报名人数</span> 
              </th>
            </tr>
<%
				String sql_con="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!search.equals(""))
				{
					sql_con+=" and pbe.id = '"+search+"'";
				}
				
				String sql = 
				"select pbe.id,\n" +
				"       pbe.name,\n" + 
				"       nvl(a.num, '0') as b_total_num,\n" + 
				"       nvl(b.num, '0') as b_uncheck_num,\n" + 
				"       nvl(c.num, '0') as b_final_num,\n" + 
				"       nvl(d.num, '0') as h_total_num,\n" + 
				"       nvl(e.num, '0') as h_uncheck_num,\n" + 
				"       nvl(f.num, '0') as h_first_num,\n" + 
				"       nvl(g.num, '0') as h_final_num,\n" + 
				"       nvl(h.num, '0') as h_reject_num,\n" + 
				"       nvl(j.total_num, '0') as total_num,\n" +
				"       nvl(nvl(j.total_num, '0') - nvl(a.num, '0') - nvl(d.num, '0'), '0') as n_total_num\n" + 
				"  from pe_bzz_exambatch pbe,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examscore e,pe_bzz_student s where s.id=e.student_id "+sql_con1+"  \n" + 
				"         group by exambatch_id) a,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examscore e,pe_bzz_student s \n" + 
				"         where status = '402880a9aaadc115062dadfcf26b0003' and s.id=e.student_id "+sql_con1+" \n" + 
				"         group by exambatch_id) b,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examscore e,pe_bzz_student s\n" + 
				"         where status = '402880a9aaadc115061dadfcf26b0003' and s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) c,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examlate e,pe_bzz_student s where s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) d,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examlate e,pe_bzz_student s\n" + 
				"         where status = 'aa2880a91dadc115011dadfcf26b0002' and s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) e,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examlate e,pe_bzz_student s\n" + 
				"         where status = 'abb2880a91dadc115011dadfcf26b0002' and s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) f,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examlate e,pe_bzz_student s\n" + 
				"         where status = 'ccb2880a91dadc115011dadfcf26b0002' and s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) g,\n" + 
				"       (select exambatch_id, count(e.id) as num\n" + 
				"          from pe_bzz_examlate e,pe_bzz_student s\n" + 
				"         where status = '1qb2880a91dadc115011dadfcf26b0002' and s.id=e.student_id "+sql_con1+"\n" + 
				"         group by exambatch_id) h,\n" + 
				"       (select e.id as exambatch_id, count(s.id) as total_num\n" + 
				"          from pe_bzz_exambatch e,\n" + 
				"               pe_bzz_batch     b,\n" + 
				"               pe_bzz_student   s,\n" + 
				"               sso_user         u\n" + 
				"         where e.batch_id = b.id\n" + 
				"           and s.fk_batch_id = b.id "+sql_con1+"\n" + 
				"           and s.flag_rank_state = '402880f827f5b99b0127f5bdadc70006'\n" + 
				"           and s.fk_sso_user_id = u.id\n" + 
				"           and u.flag_isvalid = '2'\n" + 
				"         group by e.id) j\n" + 
				" where pbe.id = a.exambatch_id(+)\n" + 
				"   and pbe.id = b.exambatch_id(+)\n" + 
				"   and pbe.id = c.exambatch_id(+)\n" + 
				"   and pbe.id = d.exambatch_id(+)\n" + 
				"   and pbe.id = e.exambatch_id(+)\n" + 
				"   and pbe.id = f.exambatch_id(+)\n" + 
				"   and pbe.id = g.exambatch_id(+)\n" + 
				"   and pbe.id = h.exambatch_id(+)\n" + 
				"   and pbe.id = j.exambatch_id(+)"+sql_con;

				//System.out.println(sql);
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
				String link="&search="+java.net.URLEncoder.encode(search,"UTF-8");
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String exambatch_id = fixnull(rs.getString("id"));
					String exambatch_name = fixnull(rs.getString("name"));
					String b_total_num = fixnull(rs.getString("b_total_num"));
					String b_uncheck_num = fixnull(rs.getString("b_uncheck_num"));
					String b_final_num = fixnull(rs.getString("b_final_num"));
					String h_total_num = fixnull(rs.getString("h_total_num"));
					String h_uncheck_num = fixnull(rs.getString("h_uncheck_num"));
					String h_first_num = fixnull(rs.getString("h_first_num"));
					String h_final_num = fixnull(rs.getString("h_final_num"));
					String h_reject_num = fixnull(rs.getString("h_reject_num"));
					String n_total_num = fixnull(rs.getString("n_total_num"));
					String total_num = fixnull(rs.getString("total_num"));
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
			<%if(us1.getRoleId().equals("402880f322736b760122737a60c40008") || us1.getRoleId().equals("402880f322736b760122737a968a0010"))  //表示为二级主管和二级辅导员
			{
			%>
			<td style='white-space: nowrap;text-align:center;'>
              <a href="/entity/manager/statistics/stat_erji_enterprise_exam_num.jsp?pageInt1=<%=pageInt%>&pageInt2=<%=pageInt %>&exambatch_id=<%=exambatch_id%>&enterprise_id=<%=enterprise_id %>&search2=<%=java.net.URLEncoder.encode(search,"UTF-8") %>" class="link"><u><font color = "#0000ff" >
              <%=exambatch_name%></font></u></a></td>
			<%
			}else{
			%>
			<td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_exam_num.jsp?pageInt1=<%=pageInt%>&exambatch_id=<%=exambatch_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%=exambatch_name%></font></u></a></td>
			<%
			}
			%>
              
              <td style='white-space: nowrap;text-align:center;'><%=total_num %></td>
              <td style='white-space: nowrap;text-align:center;'><%=b_total_num %></td>
              <td style='white-space: nowrap;text-align:center;'><%=b_uncheck_num %></td>
              <td style='white-space: nowrap;text-align:center;'><%=b_final_num %></td>
              <td style='white-space: nowrap;text-align:center;'><%=h_total_num %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=h_uncheck_num %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=h_first_num %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=h_final_num %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=h_reject_num %></td>   
              <td style='white-space: nowrap;text-align:center;'><%=n_total_num %></td>   
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
    <td height="48" align="center" class="pageBottomBorder"> 
      <%@ include file="./pub/dividepage.jsp" %>
     </td>
  </tr>
  <tr> 
    <td height="48" align="center" class="pageBottomBorder"> 
      <table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="4"><img src="../images/page_bottomSlip.gif" width="100%" height="2"></td>
        </tr>
  		<tr>
  		    <td align="center" valign="middle" class="pageBottomBorder"><span class="norm"  style="width:80px;height:15px;padding-top:3px" onmouseover="className='over'" onmouseout="className='norm'" onmousedown="className='push'" onmouseup="className='over'">
          	<%if(us1.getRoleId().equals("3")){ %>
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_exam_excel.jsp?&search=<%=java.net.URLEncoder.encode(search,"UTF-8")%>')">导出详细考试信息</span>
          	<%}else{%>
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_exam_excel_qy.jsp?&search=<%=java.net.URLEncoder.encode(search,"UTF-8")%>')">导出详细考试信息</span>
          	<%}%>
          	</span>
          	</td>
        </tr>
        </table></td>
  </tr>
</table>
</body>
</html>