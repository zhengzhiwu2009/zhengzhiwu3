<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_account_manager.xls"); %>

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
	
	String batch_id=fixnull(request.getParameter("batch_id"));
	dbpool db = new dbpool();
	MyResultSet rs = null;

    String sql = "select c.name,c.sqnum,c.jhnum,d.glnum from \n"
			+ "--授权数量 \n"
			+ " (select a.fk_role_id,a.name,a.sqnum,b.jhnum from \n"
			+ " (select s.fk_role_id, r.name, count(s.id) as sqnum \n"
			+ "   from pe_enterprise_manager e, sso_user s, pe_pri_role r \n"
			+ "  where s.id = e.fk_sso_user_id \n"
			+ "    and e.flag_isvalid = '2' \n"
			+ "    and r.id = s.fk_role_id \n"
			+ "  group by s.fk_role_id, r.name \n"
			+ "  ) a \n"
			+ "  left outer join \n"
			+ "--激活数量 \n"
			+ " (select s.fk_role_id, r.name, count(s.id) as jhnum \n"
			+ "   from pe_enterprise_manager e, sso_user s, pe_pri_role r \n"
			+ "  where s.id = e.fk_sso_user_id \n"
			+ "    and e.flag_isvalid = '2' \n"
			+ "    and r.id = s.fk_role_id and s.login_num is not null \n" 
			+ "  group by s.fk_role_id, r.name) b \n"
			+ "on a.fk_role_id=b.fk_role_id) c \n"
			+ "left outer join  \n"
			+ "--激活后有管理动作的数量 \n"
			+ "(select s.fk_role_id, r.name, count(s.id) as glnum \n"
			+ "   from pe_enterprise_manager e, sso_user s, pe_pri_role r \n"
			+ "  where s.id = e.fk_sso_user_id \n"
			+ "    and e.flag_isvalid = '2' \n"
			+ "    and r.id = s.fk_role_id and s.login_num is not null \n" 
			+ "    and e.id in (select b.fk_enterprisemanager_id from pe_bulletin b ) \n"
			+ "  group by s.fk_role_id, r.name) d \n"
			+ "on c.fk_role_id=d.fk_role_id \n"
			+ "order by c.fk_role_id "; 
		
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
<link rel=File-List href="新建%20Microsoft%20Excel%20工作表.files/filelist.xml">
<link rel=Edit-Time-Data href="新建%20Microsoft%20Excel%20工作表.files/editdata.mso">
<link rel=OLE-Object-Data href="新建%20Microsoft%20Excel%20工作表.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2010-05-04T05:11:01Z</o:LastSaved>
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
.font9
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
	font-size:18.0pt;
	font-weight:700;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:center;
	vertical-align:middle;}
.xl25
	{mso-style-parent:style0;
	text-align:center;
	vertical-align:middle;}
.xl26
	{mso-style-parent:style0;
	font-family:仿宋_GB2312, monospace;
	mso-font-charset:134;
	text-align:center;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl27
	{mso-style-parent:style0;
	font-weight:700;
	font-family:仿宋_GB2312, monospace;
	mso-font-charset:134;
	text-align:center;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:silver;
	mso-pattern:auto none;
	white-space:normal;}
.xl28
	{mso-style-parent:style0;
	font-family:仿宋_GB2312, monospace;
	mso-font-charset:134;
	text-align:justify;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:#969696;
	mso-pattern:auto none;
	white-space:normal;}
.xl29
	{mso-style-parent:style0;
	font-weight:700;
	font-family:仿宋_GB2312, monospace;
	mso-font-charset:134;
	text-align:justify;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:white;
	mso-pattern:auto none;
	white-space:normal;}
.xl30
	{mso-style-parent:style0;
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl31
	{mso-style-parent:style0;
	font-size:18.0pt;
	font-weight:700;
	font-family:黑体;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl32
	{mso-style-parent:style0;
	font-family:仿宋_GB2312, monospace;
	mso-font-charset:134;
	text-align:center;
	vertical-align:top;
	border:.5pt solid windowtext;
	background:#969696;
	mso-pattern:auto none;
	white-space:normal;}
.xl33
	{mso-style-parent:style0;
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
       <x:ActiveRow>11</x:ActiveRow>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=626 style='border-collapse:
 collapse;table-layout:fixed;width:470pt'>
 <col width=196 style='mso-width-source:userset;mso-width-alt:6272;width:147pt'>
 <col width=146 style='mso-width-source:userset;mso-width-alt:4672;width:110pt'>
 <col width=145 style='mso-width-source:userset;mso-width-alt:4640;width:109pt'>
 <col width=139 style='mso-width-source:userset;mso-width-alt:4448;width:104pt'>
 <tr height=41 style='mso-height-source:userset;height:30.75pt'>
  <td colspan=4 height=41 class=xl24 width=626 style='height:30.75pt;
  width:470pt'>管理员账号激活情况</td>
 </tr>
 <tr height=26 style='mso-height-source:userset;height:19.5pt'>
  <td colspan=4 height=26 class=xl30 style='height:19.5pt'>导出时间：<font
  class="font9"><%=getCurDate()%></font></td>
 </tr>
 <tr height=38 style='height:28.5pt'>
  <td height=38 class=xl26 width=196 style='height:28.5pt;border-top:none;
  width:147pt'><span lang=EN-US>　</span></td>
  <td class=xl27 width=146 style='border-top:none;border-left:none;width:110pt'>授权数量</td>
  <td class=xl27 width=145 style='border-top:none;border-left:none;width:109pt'>激活数量</td>
  <td class=xl27 width=139 style='border-top:none;border-left:none;width:104pt'>激活后有管理动作的数量</td>
 </tr>
 <%
 	int countsq = 0;
	int countjh = 0;
	int countgl = 0; 	
 	while(rs.next()) {
 		String rname = fixnull(rs.getString("name"));
 		int sqnum = rs.getInt("sqnum");
 		int jhnum = rs.getInt("jhnum");
 		int glnum = rs.getInt("glnum");
 		countsq += sqnum;
 		countjh += jhnum;
 		countgl += glnum;
 		
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl33 width=196 style='height:14.25pt;border-top:none;
  width:147pt'><%=rname%></td>
  <td class=xl33 width=146 style='border-top:none;border-left:none;width:110pt'
  x:num><span lang=EN-US><%=sqnum%></span></td>
  <td class=xl33 width=145 style='border-top:none;border-left:none;width:109pt'
  x:num><span lang=EN-US><%=jhnum%></span></td>
  <td class=xl33 width=139 style='border-top:none;border-left:none;width:104pt'
  x:num><span lang=EN-US><%=glnum%></span></td>
 </tr>
 <%
 	}
 	db.close(rs);
 %>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl32 width=196 style='height:14.25pt;border-top:none;
  width:147pt'>总计</td>
  <td class=xl33 style='border-top:none;border-left:none' x:num><%=countsq%></td>
  <td class=xl33 style='border-top:none;border-left:none' x:num><%=countjh%></td>
  <td class=xl33 style='border-top:none;border-left:none' x:num><%=countgl%></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=196 style='width:147pt'></td>
  <td width=146 style='width:110pt'></td>
  <td width=145 style='width:109pt'></td>
  <td width=139 style='width:104pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>
