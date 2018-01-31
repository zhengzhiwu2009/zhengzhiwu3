<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="UTF-8"%>
<%@page import="com.whaty.platform.database.oracle.*"%>
<jsp:directive.page import="com.whaty.platform.util.Page" />
<jsp:directive.page
	import="com.whaty.platform.sso.web.action.SsoConstant" />
<%@page
	import="com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.entity.bean.PeEnterprise"%>
<%@ include file="./pub/priv.jsp"%>
<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String getPercent(Double num) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(num) + "%";
	} 
	
	String getFormatNum(Double num) {
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(num);
	}
	%>
	
<%
	String search = fixnull(request.getParameter("batch_id"));
	String stuRegNo = fixnull(request.getParameter("stuRegNo"));
	//UserSession us = (UserSession)session.getAttribute(SsoConstant.SSO_USER_SESSION_KEY);
	String enterprise_id = fixnull(request
			.getParameter("enterprise_id"));
	String batch_id = fixnull(request.getParameter("batch_id"));
	//String name = fixnull(request.getParameter("name"));
	//String bSub = fixnull(request.getParameter("bSub"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	
	if(batch_id.equals(""))
		batch_id="ff8080812fce7858012fd2d27c1d1527";
	
	search=batch_id;

	List entList = us.getPriEnterprises();
	String sql_ent = "";
	String sql_entbk = "";
	for (int i = 0; i < entList.size(); i++) {
		PeEnterprise e = (PeEnterprise) entList.get(i);
		sql_ent += "'" + e.getId() + "',";
	}
	sql_entbk = sql_ent;
	if (!sql_ent.equals("")) {
		sql_ent = "(" + sql_ent.substring(0, sql_ent.length() - 1)+")";
	}
//	System.out.println("priEnt: " + sql_ent);
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
	
	//out.println("功能维护中");
	
//	System.out.println("priTag: " + priTag);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>tableType_1</title>
		<link href="/entity/manager/statistics/css/admincss.css"
			rel="stylesheet" type="text/css">
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
		<script language="javascript">
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

function changeSelect(batch_id,ent_id,erji_id)
{
	window.navigate("/entity/manager/statistics/study_student.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id);
}
function exportStudyProgress()
{
	var batch_id=document.getElementById("batch_id").value;
	var ent_id=document.getElementById("enterprise_id").value;
	var erji_id=document.getElementById("erji_id").value;
	var stuRegNo=document.getElementById("stuRegNo").value;
	var url = "/entity/manager/statistics/stat_study_progress_excel.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id+"&stuRegNo="+stuRegNo;
	//alert(url);
	if((ent_id==null || ent_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择一级企业");
	} 
	else if((batch_id==null || batch_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择学期");
		return false;
	} 
	else 
	{
		window.navigate(url);
	}
}
function exportStudyScore()
{
	var batch_id=document.getElementById("batch_id").value;
	var ent_id=document.getElementById("enterprise_id").value;
	var erji_id=document.getElementById("erji_id").value;
	var stuRegNo=document.getElementById("stuRegNo").value;
	var url = "/entity/manager/statistics/stat_study_score_excel1.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id+"&stuRegNo="+stuRegNo;
	//alert(url);
	if((ent_id==null || ent_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择一级企业");
	} 
	else if((batch_id==null || batch_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择学期");
		return false;
	} 
	else 
	{
		window.navigate(url);
	}
}
function exportStudyScoreProgress()
{
	var batch_id=document.getElementById("batch_id").value;
	var ent_id=document.getElementById("enterprise_id").value;
	var erji_id=document.getElementById("erji_id").value;
	var stuRegNo=document.getElementById("stuRegNo").value;
	var url = "/entity/manager/statistics/stat_study_score_progress_excel.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id+"&stuRegNo="+stuRegNo;
	//alert(url);
	if((ent_id==null || ent_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择一级企业");
	} 
	else if((batch_id==null || batch_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择学期");
		return false;
	} 
	else 
	{
		//window.navigate(url);
		//alert("数据维护中");
	}
}

function exportStudyExcel() {
	var batch_id=document.getElementById("batch_id").value;
	var ent_id=document.getElementById("enterprise_id").value;
	var erji_id=document.getElementById("erji_id").value;
	var stuRegNo=document.getElementById("stuRegNo").value;
	var url = "/entity/manager/statistics/study_student_excel.jsp?batch_id="+batch_id+"&enterprise_id="+ent_id+"&erji_id="+erji_id+"&stuRegNo="+stuRegNo;
	//alert(url);
	if((ent_id==null || ent_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择一级企业");
		return false;
	} 
	if((batch_id==null || batch_id=="") && (stuRegNo==null || stuRegNo=="")) 
	{
		alert("请选择学期");
		return false;
	} 
	
	window.navigate(url);
	
}
</script>
	</head>

	<body leftmargin="0" topmargin="0" class="scllbar">
		<table width="100%" border="0" cellpadding="0" cellspacing="0">
			<tr>
				<td height="28">
					<table width="100%" height="28" border="0" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="12">
								<img src="/entity/manager/statistics/images/page_titleLeft.gif"
									width="12" height="28">
							</td>
							<td align="right"
								background="/entity/manager/statistics/images/page_titleM.gif">
								<table height="28" border="0" cellpadding="0" cellspacing="0">
									<tr>
										<td width="112">
											<img
												src="/entity/manager/statistics/images/page_titleMidle.gif"
												width="112" height="28">
										</td>
										<td
											background="/entity/manager/statistics/images/page_titleRightM.gif"
											class="pageTitleText">
											学员学习信息跟踪
										</td>
									</tr>
								</table>
							</td>
							<td width="8">
								<img src="/entity/manager/statistics/images/page_titleRight.gif"
									width="8" height="28">
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td valign="top" class="pageBodyBorder">
					<!-- start:内容区域 -->

					<div class="border">
						<form name="searchForm"
							action="/entity/manager/statistics/study_student.jsp"
							method="post">
							<table width='100%' border="0" cellpadding="0" cellspacing='0'
								style='margin-bottom: 5px'>
								<tr>
									<td style='white-space: nowrap;'>
										<div style="padding-left: 6px">
											<table>

												<tr>
													<td>
														学期
													<!-- <select id="batch_id" name="batch_id" onchange="changeSelect(this.value,enterprise_id.value,erji_id.value)"> -->	
													<select id="batch_id" name="batch_id" > 
															<option value="">选择学期</option>
															<%
																String t_sql = "select id,name from pe_bzz_batch order by name";
																rs = db.executeQuery(t_sql);
																while (rs != null && rs.next()) {
																	String t_id = fixnull(rs.getString("id"));
																	String t_name = fixnull(rs.getString("name"));
																	String selected = "";
																	//System.out.println(search);
																	if (search.equals(t_id))
																		selected = "selected";
																	//System.out.println(selected);
															%>
															<option value="<%=t_id%>" <%=selected%>><%=t_name%></option>
															<%
																}
																db.close(rs);
															%>
														</select>
													</td>
												</tr>
												<tr>
													<td>
														选择一级企业:
														<select id="enterprise_id" name="enterprise_id"
															onchange="erji_id.value='';changeSelect(batch_id.value,this.value,erji_id.value)">
															<option value="">
																请选择一级企业
															</option>
															<%
																String sql_econ = "";
																
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
																//System.out.println("sql_enter: " + sql_enter);
																rs = db.executeQuery(sql_enter);
																while (rs != null && rs.next()) {
																	String selected = "";
																	String t_id = fixnull(rs.getString("id"));
																	String t_name = fixnull(rs.getString("name"));
																	String t_code = fixnull(rs.getString("code"));
																	if (enterprise_id.equals(t_id)) {  //上次选择的企业
																		selected = "selected";
																	} else if(priTag.equals("1") || priTag.equals("2")) {  //如果是一、二级管理员，只显示所在一级企业
																		selected = "selected";
																		enterprise_id = t_id;
																	}
															%>
															<option value="<%=rs.getString("id")%>" <%=selected%>>
																(<%=t_code%>)<%=rs.getString("name")%></option>
															<%
																}
																db.close(rs);
															%>
														</select>

													</td> </tr>
													<tr>
													<td style='white-space: nowrap;'>
														
															选择二级企业:
															<select id="erji_id" name="erji_id"
																onchange="changeSelect(batch_id.value,enterprise_id.value,this.value)">
																<option value="">
																	所有
																</option>
																<%
																	String sql_erji = "";
																	t_sql = "";
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
																	//System.out.println("t_sql: " + t_sql);
																	rs = db.executeQuery(t_sql);
																	while (rs != null && rs.next()) {
																		String selected = "";
																		String t_id = fixnull(rs.getString("id"));
																		String t_name = fixnull(rs.getString("name"));
																		String t_code = fixnull(rs.getString("code"));
																		if (erji_id.equals(t_id)) { //上次选择的企业
																			selected = "selected";
																		} else if(!priTag.equals("0") && sql_ent.indexOf(",") == -1) { //如果非总站管理员用户,且企业管理范围只有一个
																			selected = "selected";
																		}
																%>
																<option value="<%=t_id%>" <%=selected%>>
																	(<%=t_code%>)<%=t_name%></option>
																<%
																	}
																	db.close(rs);
																%>
															</select>
													</td>
													</tr>
													<tr>
													<td>
														学号
														<input type="text" id="stuRegNo" name="stuRegNo" value="<%=stuRegNo%>"/><span class="link"><img
																src='/entity/manager/statistics/images/buttons/search.png'
																alt='Search' width="20" height="20" title='Search'>&nbsp;<a
															href='javascript:document.searchForm.submit()'>查找</a>（选择条件后，请点击查找） </span>
													</td>
												</tr>
												<tr>
												<td>
												
												<span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a id ="getexcel" name="getexcel" href = "#" onclick="exportStudyExcel()" >导出Excel</a></span>   
												&nbsp;&nbsp;&nbsp;
											 	<span class="link">&nbsp;&nbsp;&nbsp;&nbsp;<a id ="getexcel" name="getexcel" href = "#" onclick="javascript:exportStudyProgress()" >导出学习进度</a></span>  
												&nbsp;&nbsp;&nbsp;
												<span class="link"><a id ="getpdf" href = "#" onclick="javascript:exportStudyScore()">导出作业自测</a></span>  
												&nbsp;&nbsp;&nbsp;
												<!-- <span class="link"><a id ="getpdf" href = "#" onclick="javascript:exportStudyScoreProgress()">导出综合报表</a></span> -->
												</td>
												</tr>
											</table>
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
						<table width='98%' align="center" cellpadding='1' cellspacing='0'
							class='list'>
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
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">姓名</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">学号</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">所在学期</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">所在企业</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">性别</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">出生年月</span>
								</th>
								<!--    <th width='10%' style='white-space: nowrap;'> <span class="link">职称</span></th>  -->
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">总学时</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">学习进度</span>
								</th>
								<th width='10%' style='white-space: nowrap;'>
									<span class="link">作业自测状况</span>
								</th>
								<!-- <th width='20%' style='white-space: nowrap;'> <span class="link">所属年级</span></th>
               <th width='10%' style='white-space: nowrap;'> <span class="link">设置层次专业</span></th> -->
							</tr>
							<%
								String sql_en = "";	
								String sql_con = "";
								String sql_con1 = "";
								
								if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
								{
									sql_con+=" and b.id in "+sql_ent;
									//sql_con1=" and a.fk_enterprise_id in "+sql_ent;
								}
								if(!enterprise_id.equals(""))
								{
									if(erji_id.equals(""))
									{
										sql_en = " and (b.id = '" + enterprise_id + "' or b.fk_parent_id ='"+enterprise_id+"')";
									} else {
										sql_en =" and b.id = '"+erji_id+"'";
									}
								}
								if(!batch_id.equals(""))
								{
									sql_en +="and c.id = '"+batch_id+"'";
								}
								if(stuRegNo != null && !stuRegNo.equals("")) {
									sql_en += " and a.reg_no like '%" + stuRegNo.trim() + "%'";
								}
								
								//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
								String sql_training="training_course_student tcs,";
								if(batch_id!=null && batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
								{
									sql_training="("+
											"select s.id,s.course_id,s.percent,s.learn_status,s.student_id from training_course_student s,pe_bzz_student bs where s.course_id in (select id from pr_bzz_tch_opencourse where fk_batch_id='ff8080812824ae6f012824b0a89e0008' ) and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') " + 
											"union\n" + 
											"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
											"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
											"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70419' )a,\n" + 
											"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1bab80820' )b\n" + 
											"where a.student_id=b.student_id \n" + 
											"union\n" + 
											"select decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.id,b.id) as id,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.course_id,b.course_id) as course_id,\n" + 
											"decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.percent,b.percent) as percent,decode(sign(to_number(a.percent)-to_number(b.percent)),'1',a.learn_status,b.learn_status) as learn_status,a.student_id from (\n" + 
											"select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812910e7e601291150ddc70413' )a,\n" + 
											"(select s.id,s.percent,s.student_id,s.course_id,s.learn_status from training_course_student s,pe_bzz_student bs where bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' and s.course_id='ff8080812bf5c39a012bf6a1baba0821' )b\n" + 
											"where a.student_id=b.student_id"+
											") tcs, ";
									}
								//sql_training="training_course_student tcs,";
								
								String sql = "select a.id, a.true_name as name, a.reg_no as reg_no, a.gender, a.age, to_char(a.birthday,'yyyy-mm-dd') as birth,a.title,a.fk_sso_user_id as sso_id, \n"
										+ " b.name as ent_name,c.name as b_name ,scc.ctime,scc.cpercent \n"
										+ " from pe_bzz_student a, pe_enterprise b, pe_bzz_batch c,sso_user su , \n"
										+ "      (select tcs.student_id as id,sum(tcs.percent/100 * btc.time) as ctime, sum(tcs.percent/100 * btc.time)/sum(btc.time)*100 as cpercent from "
										+sql_training+"pr_bzz_tch_opencourse bto,pe_bzz_tch_course btc \n" 
										+ "       where tcs.course_id=bto.id and bto.fk_course_id=btc.id \n"
										+ "       group by tcs.student_id) scc \n"
										+ " where c.id(+) = a.fk_batch_id and su.id=scc.id \n"
										+ "   and b.id(+) = a.fk_enterprise_id  and su.id=a.fk_sso_user_id and su.flag_isvalid='2' and a.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n" 
										+ sql_con + " "
										+ sql_en + " "
										+ sql_con1
										+ "  order by reg_no";
  
								System.out.println("-----------" + sql);
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
								String link = "&search=" + search;
								//----------分页结束---------------
								rs = db.execute_oracle_page(sql, pageInt, pagesize);
								int a = 0;
								while (rs != null && rs.next()) {
									a++;
									String id = fixnull(rs.getString("id"));
									String name = fixnull(rs.getString("name"));
									String reg_no = fixnull(rs.getString("reg_no"));
									String gender = fixnull(rs.getString("gender"));
									String age = fixnull(rs.getString("age"));
									String birth = fixnull(rs.getString("birth"));
									String title = fixnull(rs.getString("title"));
									String ent_name = fixnull(rs.getString("ent_name"));
									String b_name = fixnull(rs.getString("b_name"));
									String sso_id = fixnull(rs.getString("sso_id"));
									Double cTime = rs.getDouble("ctime");
									Double cPercent = rs.getDouble("cpercent");
							%>
							<tr class='<%if(a%2==0) {%>oddrowbg<%} else {%>evenrowbg<%} %>'>
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

								<td style='white-space: nowrap; text-align: center;'><%=name%></td>
								<td style='white-space: nowrap; text-align: center;'><%=reg_no%></td>
								<td style='white-space: nowrap; text-align: center;'><%=b_name%></td>
								<td style='white-space: nowrap; text-align: center;'><%=ent_name%></td>
								<td style='white-space: nowrap; text-align: center;'><%=gender%></td>
								<td style='white-space: nowrap; text-align: center;'><%=birth%></td>
								<td style='white-space: nowrap; text-align: center;'><%=this.getFormatNum(cTime)%></td>
								<!--   <td style='white-space: nowrap;'><%=title%></td>    -->
								<td style='white-space: nowrap; text-align: center;'>
									<a
										href="/entity/manager/statistics/stat_study_progress.jsp?pageInt1=<%=pageInt%>&id=<%=id%>&search1=<%=search%>&sso_id=<%=sso_id%>&name=<%=java.net.URLEncoder.encode(name, "UTF-8")%>"
										class="link"><u><font color="#0000ff"><%=this.getPercent(cPercent) %></font> </u> </a>
								</td>
								<td style='white-space: nowrap; text-align: center;'>
									<a
										href="/entity/manager/statistics/stat_study_score.jsp?pageInt1=<%=pageInt%>&id=<%=id%>&search1=<%=search%>&name=<%=name%>"
										class="link"><u><font color="#0000ff">查看</font> </u> </a>
								</td>
								<%--<td style='white-space: nowrap;'><%%></td>   
              <td style='white-space: nowrap;text-align:center;'><a href="pici_edu_major.jsp?pici=<%%>&pici_id=<%%>&Search=<%%>&pageInt=<%%>">设置</td> --%>
							</tr>
							<%
								}
								db.close(rs);
							%>
							<tr>
							</tr>
						</table>
						<%--          </form>--%>
						<!-- end:内容区－列表区域 -->
					</div>

					<!-- 内容区域结束 -->
				</td>
			</tr>
			<tr>
				<td height="48" align="center" class="pageBottomBorder">
					<%@ include file="./pub/dividepage.jsp"%>
				</td>
			</tr>
		</table>
		<%%>