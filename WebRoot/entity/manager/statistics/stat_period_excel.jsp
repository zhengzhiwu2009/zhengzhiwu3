<%@ page contentType="application/vnd.ms-excel;charset=UTF-8"%>
<%@page import = "com.whaty.platform.database.oracle.*,com.whaty.platform.sso.web.servlet.UserSession,java.util.*,com.whaty.platform.entity.bean.PeEnterprise"%>
<jsp:directive.page import="com.whaty.platform.util.Page,java.text.*"/>

<%	response.setHeader("Content-Disposition", "attachment;filename=stat_period.xls"); %>

<%!
	String fixnull(String str)
    {
        if(str == null || str.equals("null") || str.equals(""))
		str = "";
		return str;
     }
     
     private String getPercent(int num, double count)
    {
        if(count == 0) {
           return "0.0";
        }
        
        double res = (num / count) * 100;
        
        DecimalFormat df = new DecimalFormat("##0.0");//使用系统默认的格式  

        return df.format(res);
     }

private int checkdate(String date){
	int num =0;
	StringTokenizer stokenizer = new StringTokenizer(date,":");
	StringBuffer stringBuffer = new StringBuffer();
	while(stokenizer!=null&&stokenizer.hasMoreTokens()){
		stringBuffer.append(stokenizer.nextToken());
	}
	String tem = "1"+stringBuffer.toString();
	int temp = Integer.parseInt(tem);
	if(temp>=1000000&&temp<=1020000){
		num = 1;
	}
	if(temp>1020000&&temp<=1040000){
		num = 2;
	}
	if(temp>1040000&&temp<=1060000){
		num = 3;
	}
	if(temp>1060000&&temp<=1080000){
		num = 4;
	}
	if(temp>1080000&&temp<=1100000){
		num = 5;
	}
	if(temp>1100000&&temp<=1120000){
		num = 6;
	}
	if(temp>1120000&&temp<=1140000){
		num = 7;
	}
	if(temp>1140000&&temp<=1160000){
		num = 8;
	}
	if(temp>1160000&&temp<=1180000){
		num = 9;
	}
	if(temp>1180000&&temp<=1200000){
		num = 10;
	}
	if(temp>1200000&&temp<=1220000){
		num = 11;
	}
	if(temp>1220000&&temp<=1240000){
		num = 12;
	}
	return num;
}
%>

<%
		double count = 0;
		String temp = request.getParameter("d");
		StringTokenizer stringTokenizer = new StringTokenizer(temp,"|");		
		String [] sten = new String [stringTokenizer.countTokens()];
		int k =0;
		while(stringTokenizer.hasMoreTokens()){
			sten[k] =stringTokenizer.nextToken();
			k++;
		}
		String batch_id = fixnull(sten[0]);
		String sldate = fixnull(sten[1]);
		String eldate = fixnull(sten[2]);
		if(sldate.equals(eldate))
		{	
			sldate+=" 00:00:00";
			eldate+=" 23:59:59";
		}
		
		String sql ="  select st.start_date as sdate , st.end_date as edate "
				+	"  from stuttime st ,pe_bzz_student ps, sso_user u"
				+   "  where ps.fk_sso_user_id = st.fk_ssouser_id and ps.fk_batch_id ='"+batch_id+"' and ps.fk_sso_user_id=u.id and u.flag_isvalid='2' and ps.flag_rank_state='402880f827f5b99b0127f5bdadc70006' and (st.start_date between to_date('"+sldate+"','yyyy-MM-dd hh24:mi:ss') and to_date('"+eldate+"','yyyy-MM-dd hh24:mi:ss')+1" 
				+   "  or st.end_date between to_date('"+sldate+"','yyyy-MM-dd hh24:mi:ss') and to_date('"+eldate+"','yyyy-MM-dd hh24:mi:ss')+1)";
			//System.out.println(sql);
			dbpool db = new dbpool();
			MyResultSet rs = db.executeQuery(sql);
			int one = 0;
			int two = 0;
			int three = 0;
			int four = 0;
			int five = 0;
			int six = 0;
			int seven = 0;
			int eight = 0;
			int night = 0;
			int ten = 0;
			int eleven = 0;
			int zero = 0;
			
			
		while(rs!=null&&rs.next()){
			String sdate = rs.getString("sdate").substring(11,19);
			String edate = rs.getString("edate").substring(11,19);
			switch (this.checkdate(edate)){
				case 1:
					zero  = zero+1;
					break;
				case 2:
					one  = one+1;
					break;
				case 3:
					two  = two+1;
					break;
				case 4:
					three  = three+1;
					break;
				case 5:
					four  = four+1;
					break;
				case 6:
					five  = five+1;
					break;
				case 7:
					six  = six+1;
					break;
				case 8:
					seven  = seven+1;
					break;
				case 9:
					eight  = eight+1;
					break;
				case 10:
					night  = night+1;
					break;
				case 11:
					ten  = ten+1;
					break;
				case 12:
					eleven  = eleven+1;
					break;
			}
			switch (this.checkdate(sdate)){
			case 1:
				zero  = zero+1;
				break;
			case 2:
				one  = one+1;
				break;
			case 3:
				two  = two+1;
				break;
			case 4:
				three  = three+1;
				break;
			case 5:
				four  = four+1;
				break;
			case 6:
				five  = five+1;
				break;
			case 7:
				six  = six+1;
				break;
			case 8:
				seven  = seven+1;
				break;
			case 9:
				eight  = eight+1;
				break;
			case 10:
				night  = night+1;
				break;
			case 11:
				ten  = ten+1;
				break;
			case 12:
				eleven  = eleven+1;
				break;
		}
		}
			db.close(rs);
	  
		count = one + 
			two +
			three +
			four +
			five +
			six +
			seven +
			eight +
			night +
			ten +
			eleven;
%>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:x="urn:schemas-microsoft-com:office:excel"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 11">
<link rel=File-List href="11.files/filelist.xml">
<link rel=Edit-Time-Data href="11.files/editdata.mso">
<link rel=OLE-Object-Data href="11.files/oledata.mso">
<!--[if gte mso 9]><xml>
 <o:DocumentProperties>
  <o:Author>China User</o:Author>
  <o:LastAuthor>China User</o:LastAuthor>
  <o:Created>2010-04-26T05:31:31Z</o:Created>
  <o:LastSaved>2010-04-26T09:00:53Z</o:LastSaved>
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
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:.5pt solid windowtext;}
.xl25
	{mso-style-parent:style0;
	border-top:none;
	border-right:.5pt solid windowtext;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
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
	border-right:none;
	border-bottom:.5pt solid windowtext;
	border-left:none;}
.xl28
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
     <x:Selected/>
     <x:Panes>
      <x:Pane>
       <x:Number>3</x:Number>
       <x:RangeSelection>$A$1:$D$1</x:RangeSelection>
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

<table x:str border=0 cellpadding=0 cellspacing=0 width=288 style='border-collapse:
 collapse;table-layout:fixed;width:216pt'>
 <col width=72 span=4 style='width:54pt'>
 <tr height=29 style='mso-height-source:userset;height:21.75pt'>
  <td colspan=3 height=29 class=xl26 width=288 style='border-right:.5pt solid black;
  height:21.75pt;width:216pt'>2009测试学期<span style='mso-spacerun:yes'>  
  </span><%=sldate%>至<%=eldate%> 学习时段分布情况</td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>　</td>
  <td class=xl25>人数</td>
  <td class=xl25>比例</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>0--2</td>
  <td class=xl25 x:num><%=zero%></td>
  <td class=xl25 x:num><%=getPercent(zero, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>2--4</td>
  <td class=xl25 x:num><%=one%></td>
  <td class=xl25 x:num><%=getPercent(one, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>4--6</td>
  <td class=xl25 x:num><%=two%></td>
  <td class=xl25 x:num><%=getPercent(two, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>6--8</td>
  <td class=xl25 x:num><%=three%></td>
  <td class=xl25 x:num><%=getPercent(three, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>8--10</td>
  <td class=xl25 x:num><%=four%></td>
  <td class=xl25 x:num><%=getPercent(four, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>10--12</td>
  <td class=xl25 x:num><%=five%></td>
  <td class=xl25 x:num><%=getPercent(five, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>12--14</td>
  <td class=xl25 x:num><%=six%></td>
  <td class=xl25 x:num><%=getPercent(six, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>14--16</td>
  <td class=xl25 x:num><%=seven%></td>
  <td class=xl25 x:num><%=getPercent(seven, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>16--18</td>
 <td class=xl25 x:num><%=eight%></td>
  <td class=xl25 x:num><%=getPercent(eight, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>18--20</td>
  <td class=xl25 x:num><%=night%></td>
  <td class=xl25 x:num><%=getPercent(night, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>20--22</td>
  <td class=xl25 x:num><%=ten%></td>
  <td class=xl25 x:num><%=getPercent(ten, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>22--24</td>
  <td class=xl25 x:num><%=eleven%></td>
  <td class=xl25 x:num><%=getPercent(eleven, count)%>&nbsp;%</td>
  <td></td>
 </tr>
 <tr height=19 style='height:14.25pt'>
  <td height=19 class=xl24 style='height:14.25pt'>总计</td>
  <td class=xl25 x:num><%=(int)count%></td>
  <td class=xl25></td>
  <td></td>
 </tr>
 <![if supportMisalignedColumns]>
 <tr height=0 style='display:none'>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
  <td width=72 style='width:54pt'></td>
 </tr>
 <![endif]>
</table>

</body>

</html>


