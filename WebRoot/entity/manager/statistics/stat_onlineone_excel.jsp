<%@ page contentType="application/vnd.ms-excel;charset=GBK"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=chart_online_one.xls"); %>

<%!
	String fixnull(String str){
        if(str == null || str.equalsIgnoreCase("null"))str = "";
		return str.trim();
     }
     
     //�ж��ַ���Ϊ�յĻ�����ֵΪ"0"
	String fixnullNum(String str){
    	if(str == null || str.equalsIgnoreCase("null"))str = "0";
		return str.trim();
	}
     
     /*private String getPercent(int num, double count){
        if(count == 0) {
           return "0.0";
        }
        
        double res = (num / count) * 100;
        DecimalFormat df = new DecimalFormat("##0.0");//ʹ��ϵͳĬ�ϵĸ�ʽ  
        return df.format(res);
     }*/
     
      private String getPercent(String num, double count){
        int num_temp = Integer.parseInt(num);
        if(count == 0) {
           return "0.0";
        }
        
        double res = (num_temp / count) * 100;
        DecimalFormat df = new DecimalFormat("##0.0");//ʹ��ϵͳĬ�ϵĸ�ʽ  
        return df.format(res);
     }
     

%>

<%
	
	String sql = "";
	long sum_count = 0;
	dbpool work = new dbpool();
	MyResultSet rs ;
	
	String[][] x_date = new String[10][2];
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
	
	Date current_date = cal.getTime();
	long current_date_times = current_date.getTime();
	
	for(int i=0;i<x_date.length;i++){
		Date date_temp = new Date(current_date_times - ( 100*60*60*24*10*(9-i) ));
		x_date[i][0] = format.format(date_temp);
	}
	
	for(int i=0;i<x_date.length;i++){
		sql = " select max_num  from stat_max_onlinenum where stat_date = to_date('"+x_date[i][0]+"','yyyy-MM-dd') ";
		rs = work.executeQuery(sql);
		if(rs!=null && rs.next()){
			x_date[i][1] = fixnullNum(rs.getString("max_num"));
			sum_count = sum_count + rs.getInt("max_num");
		}
		work.close(rs);
	}
	
%>

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="chart_online_one.files/filelist.xml">
<link rel=Edit-Time-Data href="chart_online_one.files/editdata.mso">
<link rel=OLE-Object-Data href="chart_online_one.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Created>1996-12-17T01:32:42Z</o:Created>
  <o:LastSaved>2011-08-23T07:18:02Z</o:LastSaved>
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
	font-family:����;
	mso-generic-font-family:auto;
	mso-font-charset:134;
	border:none;
	mso-protection:locked visible;
	mso-style-name:����;
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
	font-family:����;
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
	border:.5pt solid windowtext;}
.xl26
	{mso-style-parent:style0;
	font-size:10.0pt;
	mso-number-format:"\@";
	text-align:right;
	vertical-align:middle;}
.xl27
	{mso-style-parent:style0;
	mso-number-format:"\@";
	text-align:right;
	vertical-align:middle;}
ruby
	{ruby-align:left;}
rt
	{color:windowtext;
	font-size:9.0pt;
	font-weight:400;
	font-style:normal;
	text-decoration:none;
	font-family:����;
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
       <x:ActiveRow>9</x:ActiveRow>
       <x:ActiveCol>5</x:ActiveCol>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=202 style='border-collapse:
 collapse;table-layout:fixed;width:152pt'>
 <col class=xl24 width=101 span=2 style='mso-width-source:userset;mso-width-alt:
 3232;width:76pt'>
 <tr height=35 style='mso-height-source:userset;height:26.25pt'>
  <td colspan=2 height=35 class=xl24 width=202 style='height:26.25pt;
  width:152pt'>ÿ�������������ͳ��</td>
 </tr>
 <tr height=18 style='mso-height-source:userset;height:13.5pt'>
  <td colspan=2 height=18 class=xl26 style='height:13.5pt'>ͳ��ʱ��:<%=x_date[x_date.length-1][0] %><span
  style='mso-spacerun:yes'>&nbsp;&nbsp;</span></td>
 </tr>
 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl25 style='height:21.95pt'>ʱ��</td>
  <td class=xl25 style='border-left:none'>����</td>
 </tr>
 
 <%
 	for(int i=0;i<x_date.length;i++){
 	%>
 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl25 style='height:21.95pt;border-top:none'><%=x_date[i][0] %></td>
  <td class=xl25 style='border-top:none;border-left:none'><%=x_date[i][1] %></td>
 </tr>
 	<%
 	}
 
 %>
 
 <tr height=29 style='mso-height-source:userset;height:21.95pt'>
  <td height=29 class=xl25 style='height:21.95pt;border-top:none'>�ϼ�</td>
  <td class=xl25 style='border-top:none;border-left:none'><%=sum_count %>��</td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=101 style='width:76pt'></td>
  <td width=101 style='width:76pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


