<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%response.setHeader("Content-Disposition", "attachment;filename=stat_study_export_zyzc_excel.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
			return str;
	}
	
	private String getDouble(double num)
    {
        DecimalFormat df = new DecimalFormat("##0.00");//使用系统默认的格式  

        return df.format(num);
     }
	
	String getCurDate() 
	{
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss E");
  		return dateformat1.format(new Date());
	}
%>
<%
	String batch_id = fixnull(request.getParameter("batch_id"));
	String type = fixnull(request.getParameter("type"));
	
	String title = "";
	if(type.equals("zy")) {
		title="作业的总体状况";
	} else {
		title="自测的总体状况";
	}
	
        String sql_con="";
		//if(!search.equals(""))
			//sql_con=" and b.id='"+search+"'";
		
		if (!batch_id.equals("")) {
			sql_con = " and s.fk_batch_id='" + batch_id + "'";
		}
		
		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String batchName = "";
		String sql = "select batch.name as bname from pe_bzz_batch batch where batch.id='"+batch_id+"'";
		rs = db.executeQuery(sql);
		
		if(rs.next()) {
			batchName = rs.getString("bname");
		}
		db.close(rs);

		if(type.equals("zy")) {//作业导出
			sql = "select a.*,b.avgs from \n"
				+ "(select c.id,c.name, c.suqnum, c.flag_coursetype, count(distinct t.user_id) pnum \n"
				+ "   from test_homeworkpaper_history t, \n"
				+ "        test_homeworkpaper_info    ti, \n"
				+ "        pe_bzz_tch_course          c, \n"
				+ "        pe_bzz_student             s, \n"
				+ "        sso_user                   u \n"
				+ "  where c.id = ti.group_id \n"
				+ "    and t.testpaper_id = ti.id \n"
				+ "    and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ "    and t.t_user_id=s.id \n"
				+ "   " + sql_con + " \n"
				+ "  group by c.flag_coursetype,c.name, c.suqnum, c.id \n"
				+ "  order by c.flag_coursetype,c.suqnum) a, \n"
				+ "(select c.id,c.name, c.suqnum, c.flag_coursetype, avg(t.score) as avgs \n"
				+ "  from test_homeworkpaper_history t, \n"
				+ "       test_homeworkpaper_info    ti, \n"
				+ "       pe_bzz_tch_course          c, \n"
				+ "       pe_bzz_student             s, \n"
				+ "       sso_user                  u \n"
				+ "  where c.id = ti.group_id \n"
				+ "   and t.testpaper_id = ti.id \n"
				+ "   and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ "   and t.t_user_id=s.id \n"
				+ "   " + sql_con + " \n"
				+ "  group by c.flag_coursetype,c.name, c.suqnum,c.id \n"
				+ "  order by c.flag_coursetype,c.suqnum) b \n"
				+ "where a.id=b.id "; 
		} else if(type.equals("zc")) {//自测导出 
			sql = "select a.*,b.avgs from \n"
				+ "(select c.id, c.name, c.suqnum, c.flag_coursetype, count(distinct t.t_user_id) as pnum \n"
				+ "  from test_testpaper_history t, \n"
				+ "       test_testpaper_info    ti, \n"
				+ "       pe_bzz_tch_course      c, \n"
				+ "       pe_bzz_student         s, \n"
				+ "       sso_user               u \n"
				+ " where c.id = ti.group_id \n"
				+ "   and t.testpaper_id = ti.id \n"
				+ "   and t.t_user_id =s.id \n"
				+ "   and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ "   " + sql_con + " \n"
				+ " group by c.flag_coursetype,c.name, c.suqnum,c.id \n"
				+ " order by c.flag_coursetype,c.suqnum ) a, \n"
				+ "(select c.id,c.name, c.suqnum, c.flag_coursetype, avg(t.score) as avgs \n"
				+ "  from test_testpaper_history t, \n"
				+ "       test_testpaper_info    ti, \n"
				+ "       pe_bzz_tch_course      c, \n"
				+ "       pe_bzz_student         s, \n"
				+ "       sso_user               u \n"
				+ "  where c.id = ti.group_id \n"
				+ "   and t.testpaper_id = ti.id \n"
				+ "    and s.fk_sso_user_id=u.id and u.flag_isvalid='2' and s.flag_rank_state='402880f827f5b99b0127f5bdadc70006' \n"
				+ "   and t.t_user_id =s.id \n"
				+ "   " + sql_con + " \n"
				+ "  group by c.flag_coursetype,c.name, c.suqnum,c.id \n"
				+ "  order by c.flag_coursetype,c.suqnum) b \n"
				+ "where a.id=b.id ";
		}
		
		//System.out.println("---------" + sql);
 %>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="作业、自测的总体状况.files/filelist.xml">
<link rel=Edit-Time-Data href="作业、自测的总体状况.files/editdata.mso">
<link rel=OLE-Object-Data href="作业、自测的总体状况.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-04-28T09:52:27Z</o:LastSaved>
  <o:Version>11.9999</o:Version>
 </o:DocumentProperties>
 <o:OfficeDocumentSettings>
  <o:RemovePersonalInformation/>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<style>
<!--table
	{mso-displayed-decimal-separator:"\.";
	mso-displayed-thousand-separator:"\,";}
@page
	{margin:1.0in .75in 1.0in .75in;
	mso-header-margin:.5in;
	mso-footer-margin:.5in;}
.font8
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
	vertical-align:bottom;
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
	vertical-align:bottom;
	border:none;
	mso-background-source:auto;
	mso-pattern:auto;
	mso-protection:locked visible;
	white-space:nowrap;
	mso-rotate:0;}
.xl24
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	text-align:justify;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	font-size:10.5pt;
	text-align:justify;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl26
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	text-align:justify;
	border-top:none;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl27
	{mso-style-parent:style0;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
	border-left:none;
	background:silver;
	mso-pattern:auto none;}
.xl28
	{mso-style-parent:style0;
	text-align:center;
	border:1.0pt solid windowtext;
	background:silver;
	mso-pattern:auto none;}
.xl29
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl30
	{mso-style-parent:style0;
	text-align:center;}
.xl31
	{mso-style-parent:style0;
	text-align:right;
	border-top:none;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:none;}
.xl32
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:none;
	border-bottom:1.0pt solid windowtext;
	border-left:1.0pt solid windowtext;}
.xl33
	{mso-style-parent:style0;
	font-size:10.5pt;
	font-family:"Times New Roman", serif;
	mso-font-charset:0;
	text-align:center;
	border-top:1.0pt solid windowtext;
	border-right:1.0pt solid windowtext;
	border-bottom:1.0pt solid windowtext;
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
     <x:CodeName>Sheet1</x:CodeName>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>10</x:ActiveRow>
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
     <x:CodeName>Sheet2</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
   <x:ExcelWorksheet>
    <x:Name>Sheet3</x:Name>
    <x:WorksheetOptions>
     <x:DefaultRowHeight>285</x:DefaultRowHeight>
     <x:CodeName>Sheet3</x:CodeName>
     <x:ProtectContents>False</x:ProtectContents>
     <x:ProtectObjects>False</x:ProtectObjects>
     <x:ProtectScenarios>False</x:ProtectScenarios>
    </x:WorksheetOptions>
   </x:ExcelWorksheet>
  </x:ExcelWorksheets>
  <x:WindowHeight>4530</x:WindowHeight>
  <x:WindowWidth>8505</x:WindowWidth>
  <x:WindowTopX>480</x:WindowTopX>
  <x:WindowTopY>120</x:WindowTopY>
  <x:AcceptLabelsInFormulas/>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=514 style='border-collapse:
 collapse;table-layout:fixed;width:386pt'>
 <col width=72 style='width:54pt'>
 <col width=252 style='mso-width-source:userset;mso-width-alt:8064;width:189pt'>
 <col width=89 style='mso-width-source:userset;mso-width-alt:2848;width:67pt'>
 <col width=101 style='mso-width-source:userset;mso-width-alt:3232;width:76pt'>
 <tr height=41 style='mso-height-source:userset;height:30.75pt'>
  <td colspan=4 height=41 class=xl29 width=514 style='height:30.75pt;
  width:386pt'><%=batchName%> <%=title%></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td colspan=4 height=20 class=xl31 style='height:15.0pt'>导出时间：<font
  class="font8"><%=getCurDate()%></font></td>
 </tr>
 <tr height=20 style='height:15.0pt'>
  <td height=20 class=xl28 style='height:15.0pt;border-top:none'>序号</td>
  <td class=xl27 style='border-top:none'>课程名称</td>
  <td class=xl27 style='border-top:none'>已做人数</td>
  <td class=xl27 style='border-top:none'>平均分</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	int index = 1;
 	int countNum = 0;
 	double countAvgs = 0;
	while(rs.next()) {
		String cName = fixnull(rs.getString("name"));
		int pNum = rs.getInt("pnum");
		double avgs = rs.getDouble("avgs");
		
		countNum += pNum;
		countAvgs += avgs;
 %>
 <tr height=22 style='mso-height-source:userset;height:16.5pt'>
  <td height=22 class=xl24 style='height:16.5pt'><%=index%></td>
  <td class=xl25><%=cName%></td>
  <td class=xl26 x:num><%=pNum%></td>
  <td class=xl26 x:num><%=getDouble(avgs)%></td>
 </tr>
   <%
 		index++;
 	}
 	db.close(rs);
 %>
 <tr height=21 style='height:15.75pt'>
  <td colspan=2 height=21 class=xl32 style='border-right:1.0pt solid black;
  height:15.75pt'>总计：</td>
  <td class=xl26 x:num><%=countNum%></td>
  <td class=xl26 x:num><%=getDouble(countAvgs)%></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=252 style='width:189pt'></td>
  <td width=89 style='width:67pt'></td>
  <td width=101 style='width:76pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
