<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=stat_study_progress_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.SimpleDateFormat"%>
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
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
%>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String enterprise_id = fixnull(request.getParameter("enterprise_id"));
	String erji_id = fixnull(request.getParameter("erji_id"));
	String stuRegNo = fixnull(request.getParameter("stuRegNo"));
	
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
	
        String sql_con="";
		String sql_con1="";
		//if(!search.equals(""))
			//sql_con=" and b.id='"+search+"'";
		String sql_en = "";	
		
		if(!sql_ent.equals("") && !us.getRoleId().equals("3"))
		{
			sql_con+=" and ent2.id in "+sql_ent;
			sql_con1=" and stu.fk_enterprise_id in "+sql_ent;
		}
		//System.out.println("sqlent:"+sql_ent);
		if(!enterprise_id.equals(""))
		{
			if(erji_id.equals("")||erji_id.equals("#"))
			{
				sql_en = " and (ent2.id = '" + enterprise_id + "' or ent2.fk_parent_id ='"+enterprise_id+"')";
			}
			else
				sql_en =" and ent2.id = '"+erji_id+"'";
		}
		if(!batch_id.equals(""))
		{
			sql_en +="and ocrs.FK_BATCH_ID= '"+batch_id+"'";
		}
		if(stuRegNo != null && !stuRegNo.equals("")) {
			sql_en += " and stu.reg_no like '%" + stuRegNo.trim() + "%'";
		}
		
		//针对2010春季学员，企业管理概览和企业管理概览（新），班组安全管理和班组安全管理（新）中选取进度高的一门，保证基础课共16门
		String sql_training="training_course_student tcs,";
		if(batch_id!=null && batch_id.equals("ff8080812824ae6f012824b0a89e0008"))
		{
		sql_training="("+
			"select course_id,percent,learn_status,student_id from training_course_student s,pe_bzz_student bs where (s.course_id <> 'ff8080812910e7e601291150ddc70419' and s.course_id <>'ff8080812bf5c39a012bf6a1bab80820' and s.course_id <>'ff8080812910e7e601291150ddc70413' and s.course_id <>'ff8080812bf5c39a012bf6a1baba0821') \n" + 
			" and bs.fk_sso_user_id=s.student_id and bs.fk_batch_id='ff8080812824ae6f012824b0a89e0008' "+
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
			") tcs,";
			}
		//sql_training="training_course_student tcs,";
		
		String sql = "select id,name,reg_no,mobile_phone,cname,percent,pname,ename,seq,flag_course_type  from ("
				+"select stu.id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent1.name as pname,ent2.name as ename,crs.suqnum as seq,ocrs.flag_course_type \n"
				+ "from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,pe_enterprise ent1,sso_user su \n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id=ent1.id \n"
				+" and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'"
				+ sql_con+ sql_con1 + sql_en + "\n"
				+" union "
				+"select stu.id,stu.true_name as name,stu.reg_no,stu.mobile_phone,crs.name as cname,tcs.percent,ent2.name as pname,ent2.name as ename ,crs.suqnum as seq,ocrs.flag_course_type\n"
				+ "from "+sql_training+" pe_bzz_student stu, pe_bzz_tch_course crs,pr_bzz_tch_opencourse ocrs,pe_enterprise ent2,sso_user su\n"
				+ "where tcs.student_id = stu.fk_sso_user_id and tcs.course_id = ocrs.id and ocrs.fk_course_id = crs.id and stu.fk_enterprise_id=ent2.id and ent2.fk_parent_id is null \n"
				+" and stu.fk_sso_user_id=su.id and su.flag_isvalid='2' and stu.flag_rank_state='402880f827f5b99b0127f5bdadc70006'"
				+ sql_con + sql_con1 + sql_en + "\n"
				+") order by reg_no,flag_course_type,to_number(seq) ";
				
		//System.out.println("----------" + sql);
 %>
 
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="Book1.files/filelist.xml">
<link rel=Edit-Time-Data href="Book1.files/editdata.mso">
<link rel=OLE-Object-Data href="Book1.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-27T04:52:39Z</o:Created>
  <o:LastSaved>2010-04-27T04:54:21Z</o:LastSaved>
  <o:Company>Micosoft</o:Company>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
.font7
	{color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:underline;
	text-underline-style:single;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;}
tr
	{mso-height-source:auto;
	mso-ruby-visibility:none;}
col
	{mso-width-source:auto;
	mso-ruby-visibility:none;}
br
	{mso-data-placement:same-cell;}
.style0
	{mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	white-space:nowrap;
	mso-rotate:0;
	mso-background-source:auto;
	mso-pattern:auto;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:常规;
	mso-style-id:0;}
td
	{mso-style-parent:style0;
	padding-top:1px;
	padding-right:1px;
	padding-left:1px;
	mso-ignore:padding;
	color:windowtext;
	font-size:12.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:General;
	text-align:general;
	vertical-align:middle;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	mso-number-format:"\@";
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-number-format:"\@";
	text-align:center;}
.xl26
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;}
.xl27
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl28
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	border:.5pt solid windowtext;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:宋体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	mso-char-type:none;
	display:none;}
-->
</style>
<!--[if gte mso 9]><xml>
 <x:ExcelWorkbook>
  <x:ExcelWorksheets>
   <x:ExcelWorksheet>
    <x:Name>Sheet1</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>14</x:ActiveRow>
       <x:ActiveCol>6</x:ActiveCol>
      </x:Pane>
     </x:Panes>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet2</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>11145</x:WindowHeight>
  <x:WindowWidth>18195</x:WindowWidth>
  <x:WindowTopX>120</x:WindowTopX>
  <x:WindowTopY>75</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=2200 style='border-collapse:
 collapse;table-layout:fixed;width:950pt'>
 <col width=61 style='mso-width-source:userset;mso-width-alt:1952;width:46pt'>
 <col width=200 style='mso-width-source:userset;mso-width-alt:3584;width:150pt'>
 <col width=125 style='mso-width-source:userset;mso-width-alt:4000;width:94pt'>
 <col width=120 style='mso-width-source:userset;mso-width-alt:3840;width:90pt'>
 <col width=230 style='mso-width-source:userset;mso-width-alt:7360;width:173pt'>
 <col width=97 style='mso-width-source:userset;mso-width-alt:3104;width:73pt'>
 <col width=189 style='mso-width-source:userset;mso-width-alt:6048;width:142pt'>
 <col width=300 style='mso-width-source:userset;mso-width-alt:7872;width:210pt'>
 <tr height=30 style='height:22.5pt'>
  <td colspan=8 height=30 class=xl25 width=1180 style='height:22.5pt;
  width:887pt'>学习进度信息报表</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=8 height=19 class=xl27 style='height:14.25pt'>导出时间:<font
  class="font7"><%=getCurDate()%><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl28 style='height:14.25pt;border-top:none'>序号</td>
  <td class=xl28 style='border-top:none;border-left:none'>姓名</td>
  <td class=xl28 style='border-top:none;border-left:none'>学号</td>
  <td class=xl28 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl28 style='border-top:none;border-left:none'>课程名称</td>
  <td class=xl28 style='border-top:none;border-left:none'>学习进度</td>
  <td class=xl28 style='border-top:none;border-left:none'>所在集团</td>
  <td class=xl28 style='border-top:none;border-left:none'>所在企业</td>
 </tr>
  <%
 	            rs = db.executeQuery(sql);
 	            int i = 1;
				while(rs!=null&&rs.next())
				{
					String fieldName = fixnull(rs.getString("name"));
					String fieldRegNo = fixnull(rs.getString("reg_no"));
					String fieldMobile = fixnull(rs.getString("mobile_phone"));
					String fieldCName = fixnull(rs.getString("cname"));
					String fieldPersent = fixnull(rs.getString("percent")); 
					String fieldPName = fixnull(rs.getString("pname")); 
					String fieldEName = fixnull(rs.getString("ename"));   
 %>

 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt;border-top:none'><%=i%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldName%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldRegNo%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldMobile%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldCName%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldPersent%>%</td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldPName%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=fieldEName%></td>
 </tr>
   <%    
   		i++;         
   		}
       db.close(rs); %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=61 style='width:46pt'></td>
  <td width=112 style='width:84pt'></td>
  <td width=125 style='width:94pt'></td>
  <td width=120 style='width:90pt'></td>
  <td width=230 style='width:173pt'></td>
  <td width=97 style='width:73pt'></td>
  <td width=189 style='width:142pt'></td>
  <td width=246 style='width:185pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


