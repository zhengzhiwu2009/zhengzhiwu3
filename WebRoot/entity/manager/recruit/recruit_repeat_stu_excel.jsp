<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import="com.sun.org.apache.bcel.internal.generic.NEW"%>
<%	response.setHeader("Content-Disposition", "attachment;filename=recruit_repeat.xls"); %>
<%@page import = "com.whaty.platform.database.oracle.*,java.util.*,java.text.*"%>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals(""))
			str = "";
		return str;
	}
	
	String getCurDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E");
		return sdf.format(new Date());
	}	
%>
<%

		dbpool db = new dbpool();
		MyResultSet rs = null;
		
		String sql = "select r1.id as id1,r1.name as name1,r1.gender as gender1,r1.folk as folk1,r1.education as edu1,pb1.name as batch1Name,r1.luru_people as lurup1,to_char(r1.luru_date,'yyyy-mm-dd') as lurud1, \n"
			+ "       r2.id as id2,r2.name as name2,r2.gender as gender2,r2.folk as folk2,r2.education as edu2,pb2.name as batch2Name,r2.luru_people as lurup2,to_char(r2.luru_date,'yyyy-mm-dd') as lurud2, \n"
			+ "       to_char(r1.birthday,'yyyy-mm-dd') as birthday,r1.mobile_phone,pe.name as pname \n"
			+ " from pe_bzz_recruit r1,pe_bzz_recruit r2,pe_enterprise pe,pe_bzz_batch pb1,pe_bzz_batch pb2 \n"
			+ "where r1.fk_enterprise_id=r2.fk_enterprise_id and r1.name=r2.name and r1.mobile_phone=r2.mobile_phone \n" 
			+ "      and r1.birthday=r2.birthday and r1.fk_enterprise_id=pe.id and r1.fk_batch_id=pb1.id and r2.fk_batch_id=pb2.id \n"
			+ "and r1.id<>r2.id and r1.fk_batch_id<>r2.fk_batch_id and r1.id > r2.id";
		//System.out.println(sql);
		
 %>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="Book1.files/filelist.xml">
<link rel=Edit-Time-Data href="Book1.files/editdata.mso">
<link rel=OLE-Object-Data href="Book1.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-08-26T07:13:52Z</o:Created>
  <o:LastSaved>2010-08-26T07:35:26Z</o:LastSaved>
  <o:Company>11</o:Company>
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
	text-align:center;}
.xl25
	{mso-style-parent:style0;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl27
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.5pt solid windowtext;}
.xl28
	{mso-style-parent:style0;
	text-align:center;
	border:.5pt solid windowtext;}
.xl29
	{mso-style-parent:style0;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl30
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl31
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:1.5pt solid windowtext;}
.xl32
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border:.5pt solid windowtext;}
.xl33
	{mso-style-parent:style0;
	font-weight:700;
	text-align:center;
	border-top:.5pt solid windowtext;
	border-right:1.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl34
	{mso-style-parent:style0;
	font-size:20.0pt;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;}
.xl35
	{mso-style-parent:style0;
	text-align:right;
	border-top:none;
	border-right:none;
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
      <x:HorizontalResolution>300</x:HorizontalResolution>
      <x:VerticalResolution>300</x:VerticalResolution>
     </x:Print>
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:ActiveRow>4</x:ActiveRow>
       <x:ActiveCol>15</x:ActiveCol>
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
  <x:WindowWidth>21435</x:WindowWidth>
  <x:WindowTopX>0</x:WindowTopX>
  <x:WindowTopY>75</x:WindowTopY>
  <x:ProtectStructure>False</x:ProtectStructure>
  <x:ProtectWindows>False</x:ProtectWindows>
 </x:ExcelWorkbook>
</xml><![endif]-->
</head>

<body link=blue vlink=purple>

<table x:str border=0 cellpadding=0 cellspacing=0 width=1178 style='border-collapse:
 collapse;table-layout:fixed;width:884pt'>
 <col width=98 style='mso-width-source:userset;mso-width-alt:3136;width:74pt'>
 <col width=72 span=15 style='width:54pt'>
 <tr height=44 style='mso-height-source:userset;height:33.0pt'>
  <td colspan=16 height=44 class=xl34 width=1178 style='height:33.0pt;
  width:884pt'>重复报名学员信息</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td colspan=16 height=19 class=xl35 style='height:14.25pt'>导出日期：<font
  class="font8"><%=getCurDate()%></font></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl25 style='height:14.25pt;border-top:none'>　</td>
  <td colspan=6 class=xl31 style='border-right:1.5pt solid black'>报名信息1</td>
  <td colspan=6 class=xl31 style='border-right:1.5pt solid black;border-left:
  none'>报名信息2</td>
  <td colspan=3 class=xl31 style='border-right:1.5pt solid black;border-left:
  none'>相同信息</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl30 style='height:14.25pt;border-top:none'>姓名</td>
  <td class=xl31 style='border-top:none'>性别</td>
  <td class=xl32 style='border-top:none;border-left:none'>民族</td>
  <td class=xl32 style='border-top:none;border-left:none'>学历</td>
  <td class=xl32 style='border-top:none;border-left:none'>批次</td>
  <td class=xl32 style='border-top:none;border-left:none'>录入人</td>
  <td class=xl33 style='border-top:none;border-left:none'>录入日期</td>
  <td class=xl31 style='border-top:none;border-left:none'>性别</td>
  <td class=xl32 style='border-top:none;border-left:none'>民族</td>
  <td class=xl32 style='border-top:none;border-left:none'>学历</td>
  <td class=xl32 style='border-top:none;border-left:none'>批次</td>
  <td class=xl32 style='border-top:none;border-left:none'>录入人</td>
  <td class=xl33 style='border-top:none;border-left:none'>录入日期</td>
  <td class=xl31 style='border-top:none;border-left:none'>出生日期</td>
  <td class=xl32 style='border-top:none;border-left:none'>移动电话</td>
  <td class=xl33 style='border-top:none;border-left:none'>所在企业</td>
 </tr>
 <%
 	rs = db.executeQuery(sql);
 	while(rs.next()) {
 		String name=fixnull(rs.getString("name1"));
 		
 		String gender1 = fixnull(rs.getString("gender1"));
 		String folk1= fixnull(rs.getString("folk1"));
 		String edu1=fixnull(rs.getString("edu1"));
 		String batch1Name=fixnull(rs.getString("batch1Name"));
 		String lurup1 = fixnull(rs.getString("lurup1"));
 		String lurud1 = fixnull(rs.getString("lurud1"));
 		
 		String gender2 = fixnull(rs.getString("gender2"));
 		String folk2= fixnull(rs.getString("folk2"));
 		String edu2=fixnull(rs.getString("edu2"));
 		String batch2Name=fixnull(rs.getString("batch2Name"));
 		String lurup2 = fixnull(rs.getString("lurup2"));
 		String lurud2 = fixnull(rs.getString("lurud2"));
 		
 		String birthday=fixnull(rs.getString("birthday"));
 		String mobile_phone = fixnull(rs.getString("mobile_phone"));
 		String pname = fixnull(rs.getString("pname"));
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl26 style='height:14.25pt;border-top:none'><%=name%></td>
  <td class=xl27 style='border-top:none'><%=gender1%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=folk1%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=edu1%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=batch1Name%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=lurup1%></td>
  <td class=xl29 style='border-top:none;border-left:none'><%=lurud1%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=gender2%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=folk2%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=edu2%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=batch2Name%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=lurup2%></td>
  <td class=xl29 style='border-top:none;border-left:none'><%=lurud2%></td>
  <td class=xl27 style='border-top:none;border-left:none'><%=birthday%></td>
  <td class=xl28 style='border-top:none;border-left:none'><%=mobile_phone%></td>
  <td class=xl29 style='border-top:none;border-left:none'><%=pname%></td>
 </tr>
 <%
 	}
 	db.close(rs);
 %>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=98 style='width:74pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>