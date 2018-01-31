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
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
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
	/*
	if(us.getRoleId().equals("2") || us.getRoleId().equals("402880a92137be1c012137db62100006"))  //表示为一级主管和一级辅导员
	{
		String t_sql="select id from pe_enterprise where fk_parent_id in "+sql_ent;
		rs=db.executeQuery(t_sql);
		while(rs!=null && rs.next())
		{
			sql_entbk+="'"+fixnull(rs.getString("id"))+"',";
		}
		db.close(rs);
		sql_ent="("+sql_entbk.substring(0,sql_entbk.length()-1)+")";
	}*/
	
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
                <td background="/entity/manager/statistics/images/page_titleRightM.gif" class="pageTitleText">查看学员统计信息</td>
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
	<form name="searchForm" action="/entity/manager/statistics/stat_student.jsp" method="post">
	<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>
    <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px">选择学期： 
				<select name="search">
		                <option value="">所有学期</option>
		                <% 
		                                               
		        String sql_t="select id,name from pe_bzz_batch order by id";
				rs=db.executeQuery(sql_t);
				while(rs!=null && rs.next())
				{
				    String id=fixnull(rs.getString("id"));
					String batch_name=fixnull(rs.getString("name"));
					String selected="";
					if(batch_name.equals(search))
							selected="selected";
				%>
			<option value=<%=batch_name %> <%=selected %>><%=batch_name %></option>
			<%
							}
					db.close(rs);
			%>
				</select>
          		<span class="link"><img src='/entity/manager/statistics/images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.searchForm.submit()'>查找</a></span>
          	</div>
			</td>
			<%if(us.getRoleId().equals("3")){ %>
			<td class='misc' style='white-space: nowrap;'>
				<div><span class="link"><a href="/entity/manager/statistics/stat_phone_list.jsp" target="_blank">导出电话跟踪促进报名的工作表</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="/entity/manager/statistics/stat_phone_list_excel.jsp" target="_blank">EXCEL</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="/entity/manager/statistics/stat_phone_list_excel1.jsp" target="_blank">EXCEL简表</a>
				&nbsp;&nbsp;&nbsp;&nbsp;<a href="/entity/manager/statistics/recruit_baoming_upload.jsp">导入预报人数</a>
				</span></div>
<%--				<div>--%>
<%--				<span class="link"><img src='/entity/manager/statistics/images/buttons/multi_delete.png' alt='Delete' width="20" height="20" title='Delete'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除选定的批次吗?")) document.userform.submit();'>删除</a></span> --%>
<%--                </div>--%>
			</td>
			<%} %>
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
              <th width='10%' style='white-space: nowrap;'> <span class="link">学期名称</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">总人数</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">完成基础课学时人数</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'><span class="link">已颁发证书人数</span> 
              </th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">结业人数</span></th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">未学完基础课人数</span></th>
              <th width='8%' style='white-space: nowrap;'> <span class="link">查看</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">性别分布</span> 
              <th width='10%' style='white-space: nowrap;'> <span class="link">年龄分布</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">学历分布</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">学习时段分布</span> 
              </th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">区域分布</span> 
              </th>
              <!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
            </tr>
<%
				String sql_con="";
				String sql_con1="";
				//if(!search.equals(""))
					//sql_con=" and b.id='"+search+"'";
				if(!sql_ent.equals(""))
				{
					sql_con+=" and e.id in "+sql_ent;
					sql_con1=" and s.fk_enterprise_id in "+sql_ent;
				}
				
				//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
				String sql_training="("+
					"select course_id,percent,learn_status,student_id from training_course_student s where s.course_id not in\n" +
					"('ff8080812910e7e601291150ddc70419','ff8080812bf5c39a012bf6a1bab80820','ff8080812910e7e601291150ddc70413','ff8080812bf5c39a012bf6a1baba0821')\n" + 
					"union\n" + 
					"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
					"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
					"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
					"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
					"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1bab80820')b\n" + 
					"where a.student_id=b.student_id\n" + 
					"union\n" + 
					"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
					"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,"+
					"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
					"select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
					"(select percent,student_id,course_id,learn_status from training_course_student s where s.course_id='ff8080812bf5c39a012bf6a1baba0821')b\n" + 
					"where a.student_id=b.student_id"+
					") cs,";
				//sql_training="training_course_student cs,";
					
				String sql = "select a.id,a.name,a.total_num,nvl(b.completed_num,0) as completed_num,(a.total_num-nvl(b.completed_num,0)) as incompleted_num from "
							+"	(select b.id,b.name,count(s.id) as total_num from (pe_bzz_batch b left outer join pe_bzz_student s on s.fk_batch_id=b.id and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006') inner join sso_user u on s.fk_sso_user_id=u.id and u.flag_isvalid='2' where 1=1 " + sql_con1 + " group by b.id,b.name) a,"
							+"	(select batch_id,batch_name,count(student_id) as completed_num from "
							+"		(select b.id as batch_id,b.name as batch_name,s.id as student_id,s.reg_no,s.name as student_name,sum(c.time) as total_time "
							+"		from pe_bzz_batch b,pe_bzz_student s,pe_enterprise e,sso_user u,"+sql_training+"pr_bzz_tch_opencourse o,pe_bzz_tch_course c "
							+"		where b.id=s.fk_batch_id(+) and e.id = s.fk_enterprise_id and b.id=o.fk_batch_id and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and o.flag_course_type = '402880f32200c249012200c780c40001' and u.id=cs.student_id and cs.course_id=o.id and o.fk_course_id=c.id and cs.learn_status='COMPLETED'  "+sql_con+" group by b.id,b.name,s.id,s.reg_no,s.name) "
							+"	where to_number(total_time)>=(select nvl(standards,0) from pe_bzz_batch where id=batch_id) group by batch_id,batch_name) b "
							+"where a.id=b.batch_id(+) and name like '%"+search+"%' order by a.name";
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
				String link="&search="+search;
				//----------分页结束---------------
				rs = db.execute_oracle_page(sql,pageInt,pagesize);
				int a = 0;
				while(rs!=null&&rs.next())
				{
					a++;
					String batch_id = fixnull(rs.getString("id"));
					String batch_name = fixnull(rs.getString("name"));
					String total_num = fixnull(rs.getString("total_num"));
					String completed_num = fixnull(rs.getString("completed_num"));
					String incompleted_num = fixnull(rs.getString("incompleted_num"));
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

              <td style='white-space: nowrap;text-align:center;'><%=batch_name%></td>
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_total_num.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%=total_num%></font></u></a></td>
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_completed_num.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%=completed_num%></font></u></a></td>
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_zhengshu_num.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%="0"%></font></u></a></td>
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_jieye_num.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%="0"%></font></u></a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_enterprise_incompleted_num.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link"><u><font color = "#0000ff" ><%=incompleted_num%></font></u></a></td>   
              <td style='white-space: nowrap;text-align:center;' ><a href="/entity/manager/statistics/stat_enterprise.jsp?pageInt1=<%=pageInt%>&id=<%=batch_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&name=<%=java.net.URLEncoder.encode(batch_name,"UTF-8") %>" class="link">查看详细</a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_xingbie.jsp?pageInt1=<%=pageInt%>&id=<%=batch_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&name=<%=java.net.URLEncoder.encode(batch_name,"UTF-8") %>" class="link">查看</a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_age.jsp?pageInt1=<%=pageInt%>&id=<%=batch_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&name=<%=java.net.URLEncoder.encode(batch_name,"UTF-8") %>" class="link">查看</a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_xueli.jsp?pageInt1=<%=pageInt%>&id=<%=batch_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>&name=<%=java.net.URLEncoder.encode(batch_name,"UTF-8") %>" class="link">查看</a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_time_select.jsp?pageInt1=<%=pageInt%>&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>&search1=<%=java.net.URLEncoder.encode(search,"UTF-8")%>" class="link">查看</a></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="/entity/manager/statistics/stat_area_excel.jsp?&batch_id=<%=batch_id%>&enterprise_id=<%=enterprise_id%>" class="link">导出</a></td>   
             
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
          	<span class="text" onclick="javascript:window.navigate('/entity/manager/statistics/stat_student_excel.jsp')">导出EXCEL</span></span></td>
        </tr>
        </table></td>
  </tr>
</table>
</body>
</html>