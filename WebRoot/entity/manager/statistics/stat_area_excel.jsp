<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_area_excel.xls"); %>

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
	
	double count = 0;
	
	UserSession us = (UserSession)session.getAttribute("user_session");
	   		
	String batch_id=fixnull(request.getParameter("batch_id"));
	String enterprise_id=fixnull(request.getParameter("enterprise_id"));
	
	String batchName = "";
	String enterpriseName = "";
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
				
		String sql_con="";
		if(!sql_ent.equals(""))
		sql_con+=" and e.id in "+sql_ent;	
		
		String sql = "";
		if(!batch_id.equals("")) {
			sql_con+=" and s.fk_batch_id='"+batch_id+"'";

			sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
			rs = db.executeQuery(sql);
		
			if(rs.next()) {
				batchName = rs.getString("bname");
			}
			db.close(rs);
		}
		
		if(!enterprise_id.equals("")) {
			sql_con+=" and (e.id='"+enterprise_id+"' or e.fk_parent_id='"+enterprise_id+"')";

			sql = "select ent.name as ename from pe_enterprise ent where ent.id='"+enterprise_id+"'";
			rs = db.executeQuery(sql);
		
			if(rs.next()) {
				enterpriseName = rs.getString("ename");
			}
			db.close(rs);
		}

		

       sql = "select e.info_sheng as sheng,e.info_shi as shi,count(s.id) as persons \n"
		+ "from pe_bzz_student s,pe_enterprise e,sso_user u \n"
		+ "where s.fk_enterprise_id=e.id and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
		+ sql_con +"\n"
		+ "group by e.info_sheng,e.info_shi \n"
		+ "order by e.info_sheng,e.info_shi\n";
		
        //System.out.println(sql);
        
        rs = db.executeQuery(sql);
		

		
			
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="人员地区分布20100316.files/filelist.xml">
<link rel=Edit-Time-Data href="人员地区分布20100316.files/editdata.mso">
<link rel=OLE-Object-Data href="人员地区分布20100316.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>微软用户</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-03-16T07:09:11Z</o:Created>
  <o:LastSaved>2010-04-28T02:55:50Z</o:LastSaved>
  <o:Company>Whaty</o:Company>
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
	border:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	text-align:center;
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	text-align:center;}
.xl27
	{mso-style-parent:style0;
	text-align:right;}
.xl28
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl30
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
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
     <x:Print>
      <x:ValidPrinterInfo/>
      <x:PaperSizeIndex>9</x:PaperSizeIndex>
      <x:HorizontalResolution>200</x:HorizontalResolution>
      <x:VerticalResolution>200</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveCol>4</x:ActiveCol>
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
  <x:WindowHeight>10695</x:WindowHeight>
  <x:WindowWidth>19275</x:WindowWidth>
  <x:WindowTopX>360</x:WindowTopX>
  <x:WindowTopY>105</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=466 style='border-collapse:
 collapse;table-layout:fixed;width:350pt'>
 <col width=58 style='mso-width-source:userset;mso-width-alt:1856;width:44pt'>
 <col width=136 style='mso-width-source:userset;mso-width-alt:4352;width:102pt'>
 <col width=181 style='mso-width-source:userset;mso-width-alt:5792;width:136pt'>
 <col width=91 style='mso-width-source:userset;mso-width-alt:2912;width:68pt'>
 <tr height=38 style='mso-height-source:userset;height:28.5pt'>
  <td colspan=4 height=38 class=xl26 width=466 style='height:28.5pt;width:350pt'><%=batchName%> <%=enterpriseName%>
  人员地区分布</td>
 </tr>
 <tr height=25 style='mso-height-source:userset;height:18.75pt'>
  <td colspan=4 height=25 class=xl27 style='height:18.75pt'>导出时间：<font
  class="font7"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 style='height:14.25pt'>序号</td>
  <td class=xl25 style='border-left:none'>省</td>
  <td class=xl25 style='border-left:none'>市</td>
  <td class=xl25 style='border-left:none'>人数</td>
 </tr>

 <%
 int i = 1;
 while(rs!=null&&rs.next())
		{
            String sheng = fixnull(rs.getString("sheng"));
			String shi = fixnull(rs.getString("shi"));
			int persons = rs.getInt("persons");
			count += persons;
 %>

  <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 align=right style='height:14.25pt;border-top:none'
  x:num><%=i%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=sheng%></td>
  <td class=xl24 style='border-top:none;border-left:none'><%=shi%></td>
  <td class=xl24 align=right style='border-top:none;border-left:none' x:num><%=persons%></td>
 </tr>
 <%				i++;
 			}
			db.close(rs);
 %>
 
 <tr height=19 style='height:14.25pt'>
  <td colspan=3 height=19 class=xl28 style='border-right:.5pt solid black;
  height:14.25pt'>总计:</td>
  <td class=xl24 align=right style='border-top:none;border-left:none' x:num><%=count%></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=58 style='width:44pt'></td>
  <td width=136 style='width:102pt'></td>
  <td width=181 style='width:136pt'></td>
  <td width=91 style='width:68pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
