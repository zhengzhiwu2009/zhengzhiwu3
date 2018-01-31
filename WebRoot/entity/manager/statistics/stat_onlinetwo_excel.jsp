<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=chart_online_two.xls"); %>

<%!
	String fixnull(String str){
        if(str == null || str.equalsIgnoreCase("null"))str = "";
		return str.trim();
     }
     
     //判断字符串为空的话，赋值为"0"
	String fixnullNum(String str){
    	if(str == null || str.equalsIgnoreCase("null"))str = "0";
		return str.trim();
	}
     
    String getIncrement(String str1,String str2){
		DecimalFormat df = new DecimalFormat("##0.0");
		
		str1 = fixnullNum(str1);
		str2 = fixnullNum(str2);
		Double double1 = Double.parseDouble(str1);
		Double double2 = Double.parseDouble(str2);
		
		return df.format(double2 - double1);
	}

%>

<%
	
	String sql = "";
	Double sum_count = 0.00;
	dbpool work = new dbpool();
	MyResultSet rs ;
	
	String[][] x_date = new String[11][3];
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
	
	Date current_date = cal.getTime();
	long current_date_times = current_date.getTime();
	
	for(int i=0;i<x_date.length;i++){
		Date date_temp = new Date(current_date_times - ( 100*60*60*24*10*(10-i) ));
		x_date[i][0] = format.format(date_temp);
	}
	
	for(int i=0;i<x_date.length;i++){
		sql = " select total_time  from stat_study_totaltime where stat_date = to_date('"+x_date[i][0]+"','yyyy-MM-dd') ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			x_date[i][1] = fixnullNum(rs.getString("total_time"));
			sum_count = sum_count + Double.parseDouble(x_date[i][1]);
		}else{
			x_date[i][1] = "0" ;
		}
		work.close(rs);
	}
	
	for(int i=1;i<x_date.length;i++){
		x_date[i][2] = getIncrement( x_date[i-1][1] , x_date[i][1]);
	}
	
%>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="chart_online_two.files/filelist.xml">
<link rel=Edit-Time-Data href="chart_online_two.files/editdata.mso">
<link rel=OLE-Object-Data href="chart_online_two.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2011-08-23T08:34:01Z</o:LastSaved>
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
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;}
.xl25
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	white-space:normal;}
.xl26
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:center;
	vertical-align:middle;
	border:.5pt solid windowtext;
	white-space:normal;}
.xl27
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:right;
	vertical-align:middle;
	border-top:none;
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;
	white-space:normal;}
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
     <x:DefaultRowHeight>439</x:DefaultRowHeight>
     <x:Unsynced/>
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
       <x:ActiveRow>2</x:ActiveRow>
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

<body link=blue vlink=purple class=xl24>

<table x:str border=0 cellpadding=0 cellspacing=0 width=415 style='border-collapse:
 collapse;table-layout:fixed;width:312pt'>
 <col class=xl24 width=125 style='mso-width-source:userset;mso-width-alt:4000;
 width:94pt'>
 <col class=xl24 width=165 style='mso-width-source:userset;mso-width-alt:5280;
 width:124pt'>
 <col class=xl24 width=125 style='mso-width-source:userset;mso-width-alt:4000;
 width:94pt'>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=3 height=35 class=xl25 width=415 style='height:26.25pt;
  width:312pt'>每天学习总时长增长值</td>
 </tr>
 <tr height=18 style='mso-height-source:userset;height:13.5pt'>
  <td colspan=3 height=18 class=xl27 width=415 style='height:13.5pt;width:312pt'
  >统计时间:<%=x_date[x_date.length-1][0] %><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;</span></td>
 </tr>
 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl26 width=125 style='height:21.95pt;border-top:none;
  width:94pt'>时间</td>
  <td class=xl26 width=165 style='border-top:none;border-left:none;width:124pt'>总时长/小时</td>
  <td class=xl26 width=125 style='border-top:none;border-left:none;width:94pt'>增长值/小时</td>
 </tr>
 <%
 	for(int i=1;i<x_date.length;i++){
 	%>
 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl26 width=125 style='height:21.95pt;border-top:none;width:94pt'><%=x_date[i][0] %></td>
  <td class=xl26 width=165 style='border-top:none;border-left:none;width:124pt'><%=x_date[i][1] %></td>
  <td class=xl26 width=125 style='border-top:none;border-left:none;width:94pt'><%=x_date[i][2] %></td>
 </tr>
 	<%	
 	}
 %>

 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl26 width=125 style='height:21.95pt;border-top:none;width:94pt'>合计</td>
  <td class=xl26 width=165 style='border-top:none;border-left:none;width:124pt'><%=sum_count %>　</td>
  <td class=xl26 width=125 style='border-top:none;border-left:none;width:94pt'>　</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=125 style='width:94pt'></td>
  <td width=165 style='width:124pt'></td>
  <td width=125 style='width:94pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>

